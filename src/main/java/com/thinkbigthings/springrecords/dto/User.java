package com.thinkbigthings.springrecords.dto;

import java.util.Set;

public record User(String username,
                   String registrationTime,
                   Set<String> roles,
                   PersonalInfo personalInfo,
                   boolean isLoggedIn) {

    public User withIsLoggedIn(boolean newValue) {
        return new User(username(), registrationTime(), roles(), personalInfo(), newValue);
    }
}

