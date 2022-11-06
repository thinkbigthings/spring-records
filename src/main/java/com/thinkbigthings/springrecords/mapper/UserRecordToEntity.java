package com.thinkbigthings.springrecords.mapper;

import com.thinkbigthings.springrecords.dto.UserAddress;
import com.thinkbigthings.springrecords.dto.UserRecord;
import com.thinkbigthings.springrecords.entity.Address;
import com.thinkbigthings.springrecords.entity.UserEntity;

import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

public class UserRecordToEntity implements Function<UserRecord, UserEntity> {

    @Override
    public UserEntity apply(UserRecord user) {
        var entity = new UserEntity(user.username(), user.email());
        entity.setAddresses(extractAddressEntities(user));
        entity.getAddresses().forEach(address -> address.setUser(entity));
        return entity;
    }

    public Set<Address> extractAddressEntities(UserRecord user) {
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
