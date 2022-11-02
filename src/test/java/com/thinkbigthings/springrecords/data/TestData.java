package com.thinkbigthings.springrecords.data;


import com.thinkbigthings.springrecords.dto.UserAddress;
import com.thinkbigthings.springrecords.dto.UserInfo;
import com.thinkbigthings.springrecords.dto.CreateUser;
import net.datafaker.Address;
import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;
import java.util.Set;

import static java.util.UUID.randomUUID;

public class TestData {

    private static Random random = new Random();

    private static Faker faker = new Faker(Locale.US, random);

    public static UserInfo randomPersonalInfo() {

        return new UserInfo(
                faker.internet().emailAddress(),
                faker.name().name(),
                Set.of(randomAddressRecord(), randomAddressRecord()));
    }

    public static CreateUser createRandomUserRegistration() {

        String username = "user-" + randomUUID();
        String password = "password";
        UserInfo info = randomPersonalInfo();

        return new CreateUser(username, password, info.email());
    }

    public static UserAddress randomAddressRecord() {

        Address fakerAddress = faker.address();
        return new UserAddress(fakerAddress.streetAddress(),
                fakerAddress.city(),
                fakerAddress.state(),
                fakerAddress.zipCode());
    }
}
