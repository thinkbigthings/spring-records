package com.thinkbigthings.springrecords.user;

import com.thinkbigthings.springrecords.dto.UserAddress;
import com.thinkbigthings.springrecords.entity.Address;
import com.thinkbigthings.springrecords.entity.User;

import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

public class UserRecordToEntity implements Function<com.thinkbigthings.springrecords.dto.User, User> {

    @Override
    public User apply(com.thinkbigthings.springrecords.dto.User user) {
        var entity = new User(user.username(), user.email());
        entity.setAddresses(extractAddressEntities(user));
        entity.getAddresses().forEach(address -> address.setUser(entity));
        return entity;
    }

    public Set<Address> extractAddressEntities(com.thinkbigthings.springrecords.dto.User user) {
        return user.addresses().stream()
                .map(this::toAddressEntity)
                .collect(toSet());
    }

    public Address toAddressEntity(UserAddress addressData) {

        var address = new Address();

        address.setLine1(addressData.line1());
        address.setCity(addressData.city());
        address.setState(addressData.state());
        address.setZip(addressData.zip());

        return address;
    }

}
