package com.thinkbigthings.springrecords.data;


import com.thinkbigthings.springrecords.dto.UserAddress;
import com.thinkbigthings.springrecords.dto.UserEditableInfo;
import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import net.datafaker.Address;
import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;
import java.util.Set;

import static java.util.UUID.randomUUID;

public class TestData {

    private static Random random = new Random();

    private static Faker faker = new Faker(Locale.US, random);

    public static UserEditableInfo randomPersonalInfo() {

        return new UserEditableInfo(
                faker.internet().emailAddress(),
                faker.name().name(),
                Set.of(randomAddressRecord(), randomAddressRecord()));
    }

    public static RegistrationRequest createRandomUserRegistration() {

        String username = "user-" + randomUUID();
        String password = "password";
        UserEditableInfo info = randomPersonalInfo();

        return new RegistrationRequest(username, password, info.email());
    }

    public static UserAddress randomAddressRecord() {

        Address fakerAddress = faker.address();
        return new UserAddress(fakerAddress.streetAddress(),
                fakerAddress.city(),
                fakerAddress.state(),
                fakerAddress.zipCode());
    }
}
