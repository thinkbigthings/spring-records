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


    @Query("SELECT u FROM User u ORDER BY u.username ASC ")
    List<User> findRecent(Pageable page);

}
