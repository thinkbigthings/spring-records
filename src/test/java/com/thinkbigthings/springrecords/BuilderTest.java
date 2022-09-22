package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.dto.BuildablePerson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BuilderTest {

    @Test
    public void testCustomBuilder() {

        BuildablePerson bilbo = BuildablePerson.newPerson()
                .withFirstName("Bilbo")
                .withLastName("Baggins");

        BuildablePerson frodo = bilbo.withFirstName("Frodo");

        assertEquals(bilbo.firstName(), "Bilbo");
        assertEquals(frodo.firstName(), "Frodo");
        assertNotEquals(bilbo, frodo);
    }

}
