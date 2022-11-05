package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.data.TestData;
import com.thinkbigthings.springrecords.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.util.HashSet;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicTest {


    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testGeneratedMethods() {

        // constructor is generated for you
        var justDataCarrier = new UserSummary("egarak", "Elim Garak");
        var plainSimpleData = new UserSummary("egarak", "Elim Garak");

        // accessor methods use the component name
        // looks odd if you're not used to it, but it is highly readable as method reference in a lambda
        assertEquals(justDataCarrier.username(), plainSimpleData.username());

        // by default records use data equality instead of reference equality
        assertEquals(justDataCarrier, plainSimpleData);

        // toString has a sensible default (data, not reference)
        // generated methods can always be overridden if you wish
        assertEquals("UserSummary[username=egarak, displayName=Elim Garak]", plainSimpleData.toString());

        // hashcodes are computed correctly for you
        // if you add a field, you don't have to worry about updating hashcode
        assertEquals(justDataCarrier.hashCode(), plainSimpleData.hashCode());
    }

    @Test
    public void testCompactConstructor() {

        // compact constructor is for validation logic
        assertThrows(IllegalArgumentException.class, () -> new UserAddress("", "", "", ""));
    }

    @Test
    public void testImmutability() {

        var addresses = new HashSet<UserAddress>();
        addresses.add(TestData.randomAddressRecord());
        addresses.add(TestData.randomAddressRecord());
        addresses.add(TestData.randomAddressRecord());

        // records are shallowly immutable, you need to manage deep immutability yourself
        User user = new User("me@springone.io", "myname", addresses);

        assertThrows(UnsupportedOperationException.class, () -> user.addresses().clear());
    }

    @Test
    public void testAnnotations() {

        // annotations are applied on record components
        var invalidUser = new User("", "", emptySet());

        // these will be applied on
        var violations = validator.validate(invalidUser);

        assertEquals(2, violations.size());
    }

    @Test
    public void testBuilder() {

        String now = Instant.now().toString();

        var user1 = new User()
                .withUsername("egarak")
                .withEmail(now);

        var user2 = new User("egarak", now, new HashSet<>());

        assertEquals(user1, user2);
    }

}
