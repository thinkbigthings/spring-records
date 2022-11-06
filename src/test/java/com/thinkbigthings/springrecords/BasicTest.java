package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.data.TestData;
import com.thinkbigthings.springrecords.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
        // it is highly readable as a method reference in a lambda
        assertEquals(justDataCarrier.username(), plainSimpleData.username());

        // by default records use data equality instead of reference equality
        assertEquals(justDataCarrier, plainSimpleData);

        // toString and hashcode are generated for you too
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
        UserRecord user = new UserRecord("me@springone.io", "myname", addresses);

        assertThrows(UnsupportedOperationException.class, () -> user.addresses().clear());
    }

    @Test
    public void testAnnotations() {

        // annotations are applied on record components
        var invalidUser = new UserRecord("", "", emptySet());

        // these will be applied on
        var violations = validator.validate(invalidUser);

        assertEquals(2, violations.size());
    }

}
