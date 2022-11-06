package com.thinkbigthings.springrecords.mapper;

import com.thinkbigthings.springrecords.dto.UserAddress;
import com.thinkbigthings.springrecords.dto.UserRecord;
import com.thinkbigthings.springrecords.entity.Address;
import com.thinkbigthings.springrecords.entity.UserEntity;

import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

public class UserEntityToRecord implements Function<UserEntity, UserRecord> {

    @Override
    public UserRecord apply(UserEntity user) {
        return new UserRecord(user.getUsername(),
                user.getEmail(),
                extractAddressRecords(user));
    }

    public Set<UserAddress> extractAddressRecords(UserEntity user) {
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
