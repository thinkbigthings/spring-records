package com.thinkbigthings.springrecords.user;


import com.thinkbigthings.springrecords.dto.UserAddress;
import com.thinkbigthings.springrecords.dto.UserRecord;
import com.thinkbigthings.springrecords.dto.UserSummary;
import com.thinkbigthings.springrecords.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String name);

    // Records don't satisfy the JPA spec
    // JPA entities will remain tied to classes because they represent mutable state transitions

    // If you want immutable JPA entities:
    // make no setter methods, have public constructor that takes all properties, annotate with Hibernate's @Immutable

    // Records make great projections:
    // - Already mapped to DTO
    // - Result is immutable
    // - No lifecycle or managed state so query can be faster

    // this is a JPQL Constructor Expression
    // nested constructors are not allowed
    // collections in constructor ars are not allowed
    @Query("SELECT new com.thinkbigthings.springrecords.dto.UserSummary" +
            "(u.username, u.email) " +
            "FROM UserEntity u " +
            "ORDER BY u.username ASC ")
    Page<UserSummary> loadSummaries(Pageable page);



    record UserDbRow(Long id, String username, String email) {}

    // Use $ instead of . as a class separator in qualified name for constructor query
    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT new com.thinkbigthings.springrecords.user.UserRepository$UserDbRow" +
            "(u.id, u.username, u.email)" +
            "FROM UserEntity u WHERE u.username=:username")
    Optional<UserDbRow> loadUserData(String username);


    @Query("SELECT new com.thinkbigthings.springrecords.dto.UserAddress(a.line1, a.city, a.state, a.zip)" +
            "FROM Address a WHERE a.user.username=:username")
    List<UserAddress> loadUserAddresses(String username);


    default Optional<UserRecord> findRecord(String username) {

        var user = loadUserData(username);

        var addresses = user
                .map(u -> loadUserAddresses(u.username()))
                .map(HashSet::new)
                .orElse(new HashSet<>());

        return user.map(u -> new UserRecord(u.username(), u.email(), addresses));
    }

}
