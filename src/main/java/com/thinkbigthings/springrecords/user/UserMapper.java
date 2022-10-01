package com.thinkbigthings.springrecords.user;

import com.thinkbigthings.springrecords.dto.AddressRecord;
import com.thinkbigthings.springrecords.dto.PersonalInfo;
import com.thinkbigthings.springrecords.entity.Address;
import com.thinkbigthings.springrecords.entity.User;

import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

public class UserMapper implements Function<User, com.thinkbigthings.springrecords.dto.User> {

    @Override
    public com.thinkbigthings.springrecords.dto.User apply(User user) {

        return new com.thinkbigthings.springrecords.dto.User(user.getUsername(),
                user.getRegistrationTime().toString(),
                toPersonalInfoRecord(user));
    }

    public PersonalInfo toPersonalInfoRecord(User user) {

        Set<AddressRecord> addresses = user.getAddresses().stream()
                .map(this::toAddressRecord)
                .collect(toSet());

        return new PersonalInfo(user.getEmail(),
                user.getDisplayName(),
                addresses);
    }

    public AddressRecord toAddressRecord(Address address) {
        return new AddressRecord(address.getLine1(),
                address.getCity(),
                address.getState(),
                address.getZip());
    }

}
