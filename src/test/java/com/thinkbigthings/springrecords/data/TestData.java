package com.thinkbigthings.springrecords.data;


import com.thinkbigthings.springrecords.dto.User;
import com.thinkbigthings.springrecords.dto.UserAddress;
import net.datafaker.Address;
import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;
import java.util.Set;


public class TestData {

    private static Random random = new Random();

    private static Faker faker = new Faker(Locale.US, random);


    public static User randomUser() {

        return new User(
                faker.name().name(),
                faker.internet().emailAddress(),
                Set.of(randomAddressRecord(), randomAddressRecord()));
    }


    public static UserAddress randomAddressRecord() {

        Address fakerAddress = faker.address();
        return new UserAddress(fakerAddress.streetAddress(),
                fakerAddress.city(),
                fakerAddress.state(),
                fakerAddress.zipCode());
    }
}
