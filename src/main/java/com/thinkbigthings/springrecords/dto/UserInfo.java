package com.thinkbigthings.springrecords.dto;

import java.util.Collections;
import java.util.Set;

// records are shallowly immutable
// records can take other records (or classes)
public record UserInfo(String email, String displayName, Set<UserAddress> addresses) {

    // we can assign to "this" in an overridden canonical constructor
    // but not in the compact constructor
    public UserInfo(String email,
                    String displayName,
                    Set<UserAddress> addresses)
    {
        this.email = email;
        this.displayName = displayName;
        this.addresses = Collections.unmodifiableSet(addresses);
    }

}

