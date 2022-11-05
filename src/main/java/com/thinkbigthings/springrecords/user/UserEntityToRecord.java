package com.thinkbigthings.springrecords.user;

import com.thinkbigthings.springrecords.dto.UserAddress;
import com.thinkbigthings.springrecords.entity.Address;
import com.thinkbigthings.springrecords.entity.User;

import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

public class UserEntityToRecord implements Function<User, com.thinkbigthings.springrecords.dto.User> {

    @Override
    public com.thinkbigthings.springrecords.dto.User apply(User user) {

        return new com.thinkbigthings.springrecords.dto.User(user.getUsername(),
                user.getEmail(),
                extractAddressRecords(user));
    }

    public Set<UserAddress> extractAddressRecords(User user) {
        return user.getAddresses().stream()
                .map(this::toAddressRecord)
                .collect(toSet());

    }

    public UserAddress toAddressRecord(Address address) {
        return new UserAddress(address.getLine1(),
                address.getCity(),
                address.getState(),
                address.getZip());
    }

}
