package com.thinkbigthings.springrecords.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

// records are shallowly immutable
public record PersonalInfo(String email,
                           String displayName,
                           String phoneNumber,
                           Set<AddressRecord> addresses) {



    // we can assign to "this" in an overridden canonical constructor
    // but not in the compact constructor
    public PersonalInfo(String email,
                        String displayName,
                        String phoneNumber,
                        Set<AddressRecord> addresses)
    {
        this.email = email;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
        this.addresses = Collections.unmodifiableSet(addresses);
    }


}

