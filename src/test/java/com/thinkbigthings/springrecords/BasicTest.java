package com.thinkbigthings.springrecords;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkbigthings.springrecords.data.TestData;
import com.thinkbigthings.springrecords.dto.AddressRecord;
import com.thinkbigthings.springrecords.dto.PersonalInfo;
import com.thinkbigthings.springrecords.dto.UserSummary;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicTest {


    @Test
    public void testValidatingConstructor() {

        assertThrows(IllegalArgumentException.class, () -> new AddressRecord("", "", "", ""));
    }

    @Test
    public void testImmutability() {

        var addresses = new HashSet<AddressRecord>();
        addresses.add(TestData.randomAddressRecord());
        addresses.add(TestData.randomAddressRecord());
        addresses.add(TestData.randomAddressRecord());

        PersonalInfo info = new PersonalInfo("myname", "me@springone.io", addresses);

        // this correctly throws an exception
        assertThrows(UnsupportedOperationException.class, () -> info.addresses().clear());

    }

    @Test
    public void testJackson() throws Exception {

        String json = """
                {
                    "username": "Bilbo2890",
                    "displayName": "bilbo@lotr.com"
                }
                """;

        UserSummary user = new ObjectMapper().readValue(json, UserSummary.class);

        assertEquals("Bilbo2890", user.username());
        assertEquals("bilbo@lotr.com", user.displayName());
    }

    @Test
    public void testDeclarations() {

        // We now have the ability to declare these locally:
        // local record classes, local enum classes, and local interfaces

        enum MyEnum { THIS, THAT, OTHER_THING }

        interface HasMyEnum {
            MyEnum thing();
        }

        record EnumWrapper(MyEnum thing) implements HasMyEnum {}

        HasMyEnum myInterface = new EnumWrapper(MyEnum.OTHER_THING);

        assertEquals(MyEnum.OTHER_THING, myInterface.thing());

        // Also new: an inner class can declare a member that is a record class.
        // To accomplish this: as of Java 16 an inner class can declare static members
        class MyInnerClass {
            public EnumWrapper enumWrapper;
            public static String NAME = "name";
        }

    }

}
