package com.thinkbigthings.springrecords.user;


import com.thinkbigthings.springrecords.dto.AddressRecord;
import com.thinkbigthings.springrecords.dto.UserSummary;
import com.thinkbigthings.springrecords.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String name);

    @Query("SELECT new com.thinkbigthings.springrecords.dto.UserSummary" +
            "(u.username, u.displayName) " +
            "FROM User u " +
            "ORDER BY u.username ASC ")
    Page<UserSummary> loadSummaries(Pageable page);

    Optional<User> findByUsername(String name);

    // "JPQL Constructor Expressions in the SELECT Clause"
    // Note that the JPQL spec does not allow for nested constructors
    // JPQL spec also does not allow passing of collections as constructor expression arguments
    // https://jakarta.ee/specifications/persistence/3.0/jakarta-persistence-spec-3.0.html#a5500
    // can we do this with JDBC for more control?
    // https://docs.spring.io/spring-data/jdbc/docs/3.0.0-M6/reference/html/#reference
    // see RowMapper
//    @Query("SELECT new com.thinkbigthings.springrecords.dto.User" +
//        "(u.username, u.registrationTime, u.email, u.displayName, u.addresses) " +
//        "FROM User u JOIN FETCH u.addresses WHERE u.username=?1")
//    Optional<com.thinkbigthings.springrecords.dto.User> findMappedUserByUsername(String name);

    @Query("SELECT u FROM User u ORDER BY u.username ASC ")
    List<User> findRecent(Pageable page);

}
