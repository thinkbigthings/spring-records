package com.thinkbigthings.springrecords.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;

import static java.util.Collections.emptySet;

// This is one possible Builder
// Good news it's an easy one-liner per method, no separate Builder class, immutable by default, no .build() at the end
// Bad news is it's a lot of boilerplate so could be error prone, and creates new object per builder method call
// alternative is https://github.com/Randgalt/record-builder
public record User(@NotNull @Size(min=5) String username, @Length(min=7) @Email String email, Set<UserAddress> addresses) {

    // we can add whatever constructors we want
    public User() {
        this("", "", emptySet());
    }

    // we can modify assignments in an overridden canonical constructor (not in compact constructor)
    public User(String username, String email, Set<UserAddress> addresses) {
        this.username = username;
        this.email = email;
        this.addresses = Collections.unmodifiableSet(addresses);
    }



    public User withUsername(String newName) {
        return new User(newName, email, addresses);
    }

    public User withEmail(String newEmail) {
        return new User(username, newEmail, addresses);
    }

    public User withAddresses(Set<UserAddress> newAddresses) {
        return new User(username, email, newAddresses);
    }
}

