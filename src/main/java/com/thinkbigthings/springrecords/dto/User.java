package com.thinkbigthings.springrecords.dto;

import static java.util.Collections.emptySet;

// This is one possible Builder
// Good news it's an easy one-liner per method, no separate Builder class, immutable by default, no .build() at the end
// Bad news is it's a lot of boilerplate so could be error prone, and creates new object per builder method call
// alternative is https://github.com/Randgalt/record-builder
public record User(String username, String registrationTime, UserInfo personalInfo) {

    public User() {
        this("", "", new UserInfo("", "", emptySet()));
    }

    public User withUsername(String newName) {
        return new User(newName, registrationTime, personalInfo);
    }

    public User withRegistrationTime(String newRegistrationTime) {
        return new User(username, newRegistrationTime, personalInfo);
    }

    public User withPersonalInfo(UserInfo newPersonalInfo) {
        return new User(username, registrationTime, newPersonalInfo);
    }
}

