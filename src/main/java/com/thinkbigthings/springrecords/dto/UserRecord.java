package com.thinkbigthings.springrecords.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;


public record UserRecord(@NotNull @Size(min=5) String username, @Length(min=7) @Email String email, Set<UserAddress> addresses) {

    public UserRecord(String username, String email) {
        this(username, email, Collections.emptySet());
    }

    // we can modify assignments in an overridden canonical constructor (not in compact constructor)
    public UserRecord(String username, String email, Set<UserAddress> addresses) {
        this.username = username;
        this.email = email;
        this.addresses = Collections.unmodifiableSet(addresses);
    }

}

