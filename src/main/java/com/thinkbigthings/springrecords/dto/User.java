package com.thinkbigthings.springrecords.dto;

import java.util.Collections;

public record User(String username, String registrationTime, PersonalInfo personalInfo) {

    public User() {
        this("", "", new PersonalInfo("", "", Collections.emptySet()));
    }

}

