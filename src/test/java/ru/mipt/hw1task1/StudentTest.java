package ru.mipt.hw1task1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    @Test
    public void ctor_shouldCreateWithName() {
        String name = "Ivan Ivanov";
        Student ivan = new Student(name);
        assertEquals(name, ivan.getName());
    }

    @Test
    public void ctor_shouldCreateWithNameAndMarks() {
        String name = "Peter Petrov";
        List<Integer> marks = List.of(5, 4, 3, 4, 5);
        Student peter = new Student(name, marks);
        assertEquals(name, peter.getName());
        List<Integer> expectedMarks = List.of(5, 4, 3, 4, 5);
        assertEquals(expectedMarks, peter.getMarks());
    }

    @Test
    public void name_shouldChangeName() {
        String name = "Svetlana Petrova";
        Student sveta = new Student(name);
        String expectedName = "Svetlana Ivanova";
        sveta.setName(expectedName);
        assertEquals(expectedName, sveta.getName());
    }

    @Test
    public void addMarks_shouldAddMarkToEmptyList() {
        String name = "Peter Petrov";
        Student peter = new Student(name);
        int expectedMark = 3;
        peter.addMark(expectedMark);
        assertEquals(List.of(3), peter.getMarks());
    }

    @Test
    public void addMarks_shouldAddMarkToNonEmptyList() {
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of(5, 4, 3, 4, 5, 2);
        Student peter = new Student(name, initialMarks);
        List<Integer> expectedMarks = List.of(5, 4, 3, 4, 5, 2, 3);
        peter.addMark(3);
        assertEquals(expectedMarks, peter.getMarks());
    }

    @Test
    public void removeMark_shouldRemoveMarksMultipleTime() {
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of(5, 4, 3, 4);
        Student peter = new Student(name, initialMarks);
        peter.removeMark(3);
        List<Integer> expectedMarks = List.of(5, 4, 4);
        assertEquals(expectedMarks, peter.getMarks());

        peter.removeMark(5);
        expectedMarks = List.of(4, 4);
        assertEquals(expectedMarks, peter.getMarks());
    }

    @Test
    public void removeMark_shouldDoNothingWhenNoSuchMark() {
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of(5, 4, 3, 4);
        Student peter = new Student(name, initialMarks);
        peter.removeMark(2);
        List<Integer> expectedMarks = List.of(5, 4, 3, 4);
        assertEquals(expectedMarks, peter.getMarks());
    }

    @Test
    public void str_shouldProvideToString() {
        String name = "Peter Petrov";
        List<Integer> marks = List.of(5, 4, 3, 4, 5, 2);
        Student peter = new Student(name, marks);
        String expected = name + ": " + "[5, 4, 3, 4, 5, 2]";
        assertEquals(expected, peter.toString());
    }

    @Test
    public void equals_shouldBeEqual() {
        String name1 = "Peter Petrov";
        List<Integer> marks1 = List.of(5, 4, 3, 4, 5, 2);
        Student peter1 = new Student(name1, marks1);

        String name2 = "Peter Petrov";
        List<Integer> marks2 = List.of(5, 4, 3, 4, 5, 2);
        Student peter2 = new Student(name2, marks2);

        String name3 = "Peter Petrov";
        List<Integer> marks3 = List.of(5, 4, 3, 4, 5, 2);
        Student peter3 = new Student(name3, marks3);

        // Reflexivity
        assertTrue(peter1.equals(peter1));

        // Symmetry
        assertTrue(peter1.equals(peter2));
        assertTrue(peter2.equals(peter1));

        // Transitivity
        assertTrue(peter2.equals(peter1));
        assertTrue(peter3.equals(peter1));

        // not equal to NULL
        assertFalse(peter1.equals(null));
    }

    @Test
    public void equals_shouldNotBeEqualWhenNotSameName() {
        String name1 = "Peter Petro";
        List<Integer> marks1 = List.of(5, 4, 3, 4, 5, 2);
        Student peter1 = new Student(name1, marks1);

        String name2 = "Peter Petrov";
        List<Integer> marks2 = List.of(5, 4, 3, 4, 5, 2);
        Student peter2 = new Student(name2, marks2);

        assertFalse(peter2.equals(peter1));
    }

    @Test
    public void equals_shouldNotBeEqualWhenNotSameMarks() {
        String name1 = "Peter Petrov";
        List<Integer> marks1 = List.of(5, 4, 3, 4, 5, 2);
        Student peter1 = new Student(name1, marks1);

        String name2 = "Peter Petrov";
        List<Integer> marks2 = List.of(5, 4, 3, 4, 5);
        Student peter2 = new Student(name2, marks2);

        assertFalse(peter2.equals(peter1));
    }

    @Test
    public void equals_shouldNotBeEqualWhenNotStudent() {
        String name = "Peter Petrov";
        List<Integer> marks = List.of(5, 4, 3, 4, 5, 2);
        Student peter = new Student(name, marks);

        assertFalse(peter.equals("Hello World"));
    }

    @Test
    public void hash_shouldHaveSameHashCodeWhenEqualObjects() {
        String name1 = "Peter Petrov";
        List<Integer> marks1 = List.of(5, 4, 3, 4, 5, 2);
        Student peter1 = new Student(name1, marks1);

        String name2 = "Peter Petrov";
        List<Integer> marks2 = List.of(5, 4, 3, 4, 5, 2);
        Student peter2 = new Student(name2, marks2);

        assertTrue(peter2.equals(peter1));
        assertEquals(peter2.hashCode(), peter1.hashCode());
    }

    @Test
    public void hash_shouldHaveDifferentHashCodeWhenNotEqualObjects() {
        String name1 = "Peter Petr";
        List<Integer> marks1 = List.of(5, 4, 3, 4, 5, 2);
        Student peter1 = new Student(name1, marks1);

        String name2 = "Peter Petrov";
        List<Integer> marks2 = List.of(5, 4, 3, 4, 5, 2);
        Student peter2 = new Student(name2, marks2);

        assertFalse(peter2.equals(peter1));
        assertNotEquals(peter2.hashCode(), peter1.hashCode());
    }
}
