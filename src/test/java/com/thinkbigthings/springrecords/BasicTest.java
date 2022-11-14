package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.data.TestData;
import com.thinkbigthings.springrecords.dto.*;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicTest {


    @Test
    public void testGeneratedMethods() {

        // constructor is generated for you
        var justDataCarrier = new UserSummary("egarak", "egarak@ds9.gov");
        var plainSimpleData = new UserSummary("egarak", "egarak@ds9.gov");

        // accessor methods use the component name
        // it is highly readable as a method reference in a lambda
        assertEquals(justDataCarrier.username(), plainSimpleData.username());

        // by default records use data equality instead of reference equality
        assertEquals(justDataCarrier, plainSimpleData);

        // equals, hashcode, and toString are generated for you too
    }

    @Test
    public void testInheritance() {
        String recordParentClass = UserSummary.class.getSuperclass().getName();
        assertEquals("java.lang.Record", recordParentClass);
    }

    @Test
    public void testGenerics() {

        // records can be declared inline
        record Container<T>(T content) {}

        // Here the generic type is inferred by the argument type.
        var container1 = new Container<>(137);
        var container2 = new Container<>("137");

        // note the return types of content()
        assertEquals(container1.content().toString(), container2.content());
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
        UserRecord user = new UserRecord("name", "together@springone.io", addresses);

        assertThrows(UnsupportedOperationException.class, () -> user.addresses().clear());
    }

    @Test
    public void testAnnotations() {

        var validator = Validation.buildDefaultValidatorFactory().getValidator();

        // annotations are applied on record components
        var invalidUser = new UserRecord("", "");

        // these will be applied on
        var violations = validator.validate(invalidUser);

        assertEquals(2, violations.size());
    }

}
