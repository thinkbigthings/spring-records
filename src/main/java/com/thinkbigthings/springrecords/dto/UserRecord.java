package com.thinkbigthings.springrecords.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;


public record UserRecord(@NotNull @Size(min=5) String username, @Length(min=7) @Email String email, Set<UserAddress> addresses) {

    // we can modify assignments in an overridden canonical constructor (not in compact constructor)
    public UserRecord(String username, String email, Set<UserAddress> addresses) {
        this.username = username;
        this.email = email;
        this.addresses = Collections.unmodifiableSet(addresses);
    }

}

