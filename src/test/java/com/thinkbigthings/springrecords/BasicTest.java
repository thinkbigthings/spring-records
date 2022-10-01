package com.thinkbigthings.springrecords;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicTest {


    @Test
    public void basicRecords() {

        // fields are called "record components"
        record MinMax(int min, int max) { }
        record Range(int min, int max) { }

        // can't assign a Range to a MinMax or cast to it (we don't have structural typing)
        // would need to define an interface that matches the generated methods

        // records can take other records as arguments
        record Range2D(Range x, Range y) { }

        // does not re-use instances like String... Maybe later?
        MinMax m = new MinMax(0, 0);
        MinMax m2 = new MinMax(0, 0);

        assertEquals(m, m2);
    }

    @Test
    public void testValidatingConstructor() {


        record Point3D(int x, int y, int z) {
            Point3D() {
                this(0,0,0);
            }
            public Point3D withX(int newX) {
                return new Point3D(newX, y, z);
            }
        }
        var point = new Point3D();
        point = point.withX(5);
        System.out.println(point);

        record PositiveInt(int value) {
            PositiveInt {
                // compact constructor is intended for validation
                // we could also assign "value" here (but not "this.value")
                if(value <= 0) {
                    throw new IllegalArgumentException("Value was "+ value);
                }

            }
        }

        assertThrows(IllegalArgumentException.class, () -> new PositiveInt(-1));

        assertEquals(1, new PositiveInt(1).value());
    }

    @Test
    public void testImmutability() {

        // records are shallowly immutable
        record WordList(List<String> words) {

            // we can assign to "this" in an overridden canonical constructor
            // but not in the compact constructor
            public WordList(List<String> words) {
                this.words = Collections.unmodifiableList(words);
            }
        }

        ArrayList<String> mutableWords = new ArrayList<>();
        mutableWords.add("The");
        mutableWords.add("fox");
        mutableWords.add("jumped");

        WordList list = new WordList(mutableWords);

        // this correctly throws an exception
        assertThrows(UnsupportedOperationException.class, () -> list.words().clear());

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
        // To accomplish this: in Java 16 an inner class can declare a member that is explicitly or implicitly static
        class MyInnerClass {
            public EnumWrapper enumWrapper;
            public static String NAME = "name";
        }

    }

}
