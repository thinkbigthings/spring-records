package com.thinkbigthings.springrecords.user;


import com.thinkbigthings.springrecords.dto.AddressRecord;
import com.thinkbigthings.springrecords.dto.PersonalInfo;
import com.thinkbigthings.springrecords.dto.UserSummary;
import com.thinkbigthings.springrecords.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String name);

    Optional<User> findByUsername(String name);

    @Query("SELECT new com.thinkbigthings.springrecords.dto.UserSummary" +
            "(u.username, u.displayName) " +
            "FROM User u " +
            "ORDER BY u.username ASC ")
    Page<UserSummary> loadSummaries(Pageable page);



    record UserDbRow(Long id, String username, Instant registrationTime, String email, String display) {}

    // Use $ instead of . as a class separator in qualified name for constructor query
    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT new com.thinkbigthings.springrecords.user.UserRepository$UserDbRow" +
            "(u.id, u.username, u.registrationTime, u.email, u.displayName)" +
            "FROM User u WHERE u.username=:username")
    Optional<UserDbRow> loadUserData(String username);


    @Query("SELECT new com.thinkbigthings.springrecords.dto.AddressRecord(a.line1, a.city, a.state, a.zip)" +
            "FROM Address a WHERE a.user.username=:username")
    List<AddressRecord> loadUserAddresses(String username);


    default Optional<com.thinkbigthings.springrecords.dto.User> findRecord(String username) {

        var user = loadUserData(username);

        var addresses = user
                .map(u -> loadUserAddresses(u.username()))
                .map(HashSet::new)
                .orElse(new HashSet<>());

        // gets around nested constructor expressions, gets around collections in constructor expressions, and shows builder
        return user.map(u -> new com.thinkbigthings.springrecords.dto.User()
                .withUsername(u.username())
                .withRegistrationTime(u.registrationTime().toString())
                .withPersonalInfo(new PersonalInfo(u.email(), u.display(), addresses)));
    }


}
