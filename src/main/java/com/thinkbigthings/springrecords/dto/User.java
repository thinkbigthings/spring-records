package com.thinkbigthings.springrecords.dto;

import static java.util.Collections.emptySet;

// This is one possible Builder
// Good news it's an easy one-liner per method, no separate Builder class, immutable by default, no .build() at the end
// Bad news is it's a lot of boilerplate so could be error prone, and creates new object per builder method call
public record User(String username, String registrationTime, UserEditableInfo personalInfo) {

    public User() {
        this("", "", new UserEditableInfo("", "", emptySet()));
    }

    public User withUsername(String newName) {
        return new User(newName, registrationTime, personalInfo);
    }

    public User withRegistrationTime(String newRegistrationTime) {
        return new User(username, newRegistrationTime, personalInfo);
    }

    public User withPersonalInfo(UserEditableInfo newPersonalInfo) {
        return new User(username, registrationTime, newPersonalInfo);
    }
}

