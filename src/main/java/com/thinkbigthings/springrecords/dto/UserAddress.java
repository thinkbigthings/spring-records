package com.thinkbigthings.springrecords.dto;

import static java.lang.String.join;

public record UserAddress(String line1, String city, String state, String zip) {

    // compact constructor is intended for validation
    // called from constructor
    public UserAddress {
        if(line1.isBlank() || city.isBlank() || state.isBlank() || zip.isBlank() ) {
            throw new IllegalArgumentException("Data is missing, found: " + join(", ", line1, city, state, zip));
        }
    }

}