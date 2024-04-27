package dk.kea.dat3js.hogwarts5.students;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void getFullNameWithMiddleName() {
        //arrange
        Student student = new Student("Harry", "James", "Potter", null, 1, "male", false);

        //act
        String fullName = student.getFullName();

        //assert
        assertEquals("Harry James Potter", fullName);
    }

    @Test
    void getFullNameWithoutMiddleName() {
        //arrange
        Student student = new Student("Harry", null, "Potter", null, 1, "male", false);

        //act
        String fullName = student.getFullName();

        //assert
        assertEquals("Harry Potter", fullName);
    }

    @Test
    void setFullNameWithMiddleName() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1, "male", false);

        //act
        student.setFullName("Harry James Potter");

        //assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James", student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }

    @Test
    void setFullNameWithoutMiddleName() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1, "male", false);

        //act
        student.setFullName("Harry Potter");

        //assert
        assertEquals("Harry", student.getFirstName());
        assertNull(student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }

    @Test
    void setFullNameWithOnlyFirstName() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1, "male", false);

        //act
        student.setFullName("Harry");

        //assert
        assertEquals("Harry", student.getFirstName());
        assertNull(student.getMiddleName());
        assertNull(student.getLastName());
    }

    @Test
    void setFullNameWithMultipleMiddleNames() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1, "male", false);

        //act
        student.setFullName("Harry James Sirius Potter");

        //assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James Sirius", student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }

    @Test
    void setFullNameWithEmptyString() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1, "male", false);

        //act
        student.setFullName("");

        //assert
        assertEquals("", student.getFirstName());
        assertNull(student.getMiddleName());
        assertNull(student.getLastName());
    }

    @Test
    void setFullNameWithNull() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1, "male", false);

        //act
        student.setFullName(null);

        //assert
        assertEquals("First", student.getFirstName());
        assertEquals("Middle", student.getMiddleName());
        assertEquals("Last", student.getLastName());
    }

    @Test
    void capitalizeIndividualNames() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1, "male", false);

        //act
        student.setFirstName("harry");
        student.setMiddleName("james");
        student.setLastName("potter");

        //assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James", student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }

    @Test
    void capitalizeIndividualNamesWithCrazyCapitalization() {
        //arrange
        Student student = new Student("first", "middle", "last", null, 1,"male", false);

        //act
        student.setFirstName("hArRy");
        student.setMiddleName("jAmEs");
        student.setLastName("pOtTeR");

        //assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James", student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }
}