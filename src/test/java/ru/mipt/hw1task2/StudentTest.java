package ru.mipt.hw1task2;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    @Test
    public void ctor_shouldCreateWithName() {
        String name = "Ivan Ivanov";
        Student<String> ivan = new Student<>(name);
        assertEquals(name, ivan.getName());
    }

    @Test
    public void ctor_shouldCreateWithNameAndGenericMarksNoValidityRule() {
        // Marks are integers
        String name = "Peter Petrov";
        List<Integer> marksInt = List.of(5, 4, 3, 4, 5);
        Student<Integer> peter = new Student<>(name, marksInt);
        assertEquals(name, peter.getName());
        List<Integer> expectedMarksInt = List.of(5, 4, 3, 4, 5);
        assertEquals(expectedMarksInt, peter.getMarks());

        // Marks are strings
        name = "Ivan Ivanov";
        List<String> marksStr = List.of("Pass", "Pass", "Not Pass");
        Student<String> ivan = new Student<>(name, marksStr);
        assertEquals(name, ivan.getName());
        List<String> expectedMarksStr = List.of("Pass", "Pass", "Not Pass");
        assertEquals(expectedMarksStr, ivan.getMarks());

        // Marks are dates
        name = "Sidor Sidorov";
        List<LocalDate> marksDate = List.of(
                LocalDate.of(2026, 2, 2),
                LocalDate.of(2026, 2, 3),
                LocalDate.of(2026, 2, 12)
        );
        Student<LocalDate> sidor = new Student<>(name, marksDate);
        assertEquals(name, sidor.getName());
        List<LocalDate> expectedMarksDate = List.of(
                LocalDate.of(2026, 2, 2),
                LocalDate.of(2026, 2, 3),
                LocalDate.of(2026, 2, 12)
        );
        assertEquals(expectedMarksDate, sidor.getMarks());
    }

    @Test
    public void ctor_shouldCreateWithNameAndGenericMarksWithValidityRule() {
        // Marks are integers
        String name = "Peter Petrov";
        List<Integer> marks = List.of(5, 4, 3, 4, 5);
        Predicate<Integer> validityRuleInt = mark -> (mark >= 2) && (mark <= 5);
        Student<Integer> peter = new Student<>(name, marks, validityRuleInt);
        assertEquals(name, peter.getName());
        List<Integer> expectedMarks = List.of(5, 4, 3, 4, 5);
        assertEquals(expectedMarks, peter.getMarks());

        // Marks are strings
        name = "Ivan Ivanov";
        List<String> marksStr = List.of("Pass", "Pass", "Not Pass");
        Predicate<String> validityRuleStr = mark -> "pass".equalsIgnoreCase(mark) || "not pass".equalsIgnoreCase(mark);
        Student<String> ivan = new Student<>(name, marksStr, validityRuleStr);
        assertEquals(name, ivan.getName());
        List<String> expectedMarksStr = List.of("Pass", "Pass", "Not Pass");
        assertEquals(expectedMarksStr, ivan.getMarks());

        // Marks are dates
        name = "Sidor Sidorov";
        List<LocalDate> marksDate = List.of(
                LocalDate.of(2026, 2, 2),
                LocalDate.of(2026, 2, 3),
                LocalDate.of(2026, 2, 12)
        );
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 7, 1);
        Predicate<LocalDate> validityRuleDate = mark -> mark.isAfter(startDate) && mark.isBefore(endDate);
        Student<LocalDate> sidor = new Student<>(name, marksDate, validityRuleDate);
        assertEquals(name, sidor.getName());
        List<LocalDate> expectedMarksDate = List.of(
                LocalDate.of(2026, 2, 2),
                LocalDate.of(2026, 2, 3),
                LocalDate.of(2026, 2, 12)
        );
        assertEquals(expectedMarksDate, sidor.getMarks());
    }

    @Test
    public void ctor_shouldThrowWhenSomeMarkIsInvalid() {
        // Marks are integers
        List<Integer> marksInt = List.of(5, 4, 3, 6, 5);
        Predicate<Integer> validityRuleInt = mark -> (mark >= 2) && (mark <= 5);
        assertThrows(IllegalArgumentException.class, () -> new Student<>("Peter", marksInt, validityRuleInt));

        // Marks are strings
        List<String> marksStr = List.of("Pass", "Pas", "Not Pass");
        Predicate<String> validityRuleStr = mark -> "pass".equalsIgnoreCase(mark) || "not pass".equalsIgnoreCase(mark);
        assertThrows(IllegalArgumentException.class, () -> new Student<>("Ivan", marksStr, validityRuleStr));

        // Marks are dates
        List<LocalDate> marksDate = List.of(
                LocalDate.of(2025, 12, 31)
        );
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 7, 1);
        Predicate<LocalDate> validityRuleDate = mark -> mark.isAfter(startDate) && mark.isBefore(endDate);
        assertThrows(IllegalArgumentException.class, () -> new Student<>("Sidor", marksDate, validityRuleDate));
    }

    @Test
    public void name_shouldChangeName() {
        String name = "Svetlana Petrova";
        Student<String> sveta = new Student<>(name);
        String expectedName = "Svetlana Ivanova";
        sveta.setName(expectedName);
        assertEquals(expectedName, sveta.getName());
    }

    @Test
    public void marks_shouldAddMarkToEmptyListNoValidityRule() {
        String name = "Peter Petrov";
        Student<Integer> peter = new Student<>(name);
        int expectedMark = 3;
        peter.addMark(expectedMark);
        assertEquals(List.of(expectedMark), peter.getMarks());
    }

    @Test
    public void marks_shouldAddMarkToNonEmptyList() {
        // Marks are integers
        List<Integer> marksInt = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter = new Student<>("Peter Petrov", marksInt);
        peter.addMark(3);
        List<Integer> expectedMarksInt = List.of(5, 4, 3, 4, 5, 2, 3);
        assertEquals(expectedMarksInt, peter.getMarks());

        // Marks are strings
        List<String> marksStr = List.of("Pass", "Not pass");
        Predicate<String> validityRuleStr = mark -> "pass".equalsIgnoreCase(mark) || "not pass".equalsIgnoreCase(mark);
        Student<String> ivan = new Student<>("Ivan Ivanov", marksStr, validityRuleStr);
        ivan.addMark("Pass");
        List<String> expectedMarksStr = List.of("Pass", "Not pass", "Pass");;
        assertEquals(expectedMarksStr, ivan.getMarks());

        // Marks are dates
        List<LocalDate> marksDate = List.of(LocalDate.of(2026, 2, 2));
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 7, 1);
        Predicate<LocalDate> validityRuleDate = mark -> mark.isAfter(startDate) && mark.isBefore(endDate);
        Student<LocalDate> sidor = new Student<>("Sidor Sidorov", marksDate, validityRuleDate);
        sidor.addMark(LocalDate.of(2026, 4, 12));
        List<LocalDate> expectedMarksDate =  List.of(
                LocalDate.of(2026, 2, 2),
                LocalDate.of(2026, 4, 12));
        assertEquals(expectedMarksDate, sidor.getMarks());
    }

    @Test
    public void marks_shouldThrowWhenAddNonValidMark() {
        // Marks are integers
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of();
        Predicate<Integer> validityRuleInt = mark -> (mark >= 2) && (mark <= 5);
        Student<Integer> peter = new Student<>(name, initialMarks, validityRuleInt);
        assertThrows(IllegalArgumentException.class, () -> peter.addMark(1));
        assertThrows(IllegalArgumentException.class, () -> peter.addMark(6));

        // Marks are strings
        name = "Ivan Ivanov";
        List<String> marksStr = List.of();
        Predicate<String> validityRuleStr = mark -> "pass".equalsIgnoreCase(mark) || "not pass".equalsIgnoreCase(mark);
        Student<String> ivan = new Student<>(name, marksStr, validityRuleStr);
        assertThrows(IllegalArgumentException.class, () -> ivan.addMark("pas"));

        // Marks are dates
        name = "Sidor Sidorov";
        List<LocalDate> marksDate = List.of();
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 7, 1);
        Predicate<LocalDate> validityRuleDate = mark -> mark.isAfter(startDate) && mark.isBefore(endDate);
        Student<LocalDate> sidor = new Student<>(name, marksDate, validityRuleDate);
        assertThrows(IllegalArgumentException.class, () -> sidor.addMark(startDate));
        assertThrows(IllegalArgumentException.class, () -> sidor.addMark(endDate));
    }

    @Test
    public void marks_shouldRemoveMarkFromEmptyListWithNoEffect() {
        String name = "Peter Petrov";
        Student<Integer> peter = new Student<>(name);
        int someIndex = 5;
        peter.removeMark(someIndex);
        assertEquals(0, peter.getMarks().size());
    }

    @Test
    public void marks_shouldRemoveMarkFromNonEmptyList() {
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of(5, 4, 3, 4);
        Student<Integer> peter = new Student<>(name, initialMarks);
        peter.removeMark(3);
        List<Integer> expectedMarks = List.of(5, 4, 4);
        assertEquals(expectedMarks, peter.getMarks());

        peter.removeMark(5);
        expectedMarks = List.of(4, 4);
        assertEquals(expectedMarks, peter.getMarks());
    }

    @Test
    public void str_shouldProvideToString() {
        String name = "Peter Petrov";
        List<Integer> marks = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter = new Student<>(name, marks);
        String expected = name + ": " + "[5, 4, 3, 4, 5, 2]";
        assertEquals(expected, peter.toString());
    }

    @Test
    public void equals_shouldBeEqual() {
        String name1 = "Peter Petrov";
        List<Integer> marks1 = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter1 = new Student<>(name1, marks1);

        String name2 = "Peter Petrov";
        List<Integer> marks2 = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter2 = new Student<>(name2, marks2);

        String name3 = "Peter Petrov";
        List<Integer> marks3 = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter3 = new Student<>(name3, marks3);

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
        Student<Integer> peter1 = new Student<>(name1, marks1);

        String name2 = "Peter Petrov";
        List<Integer> marks2 = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter2 = new Student<>(name2, marks2);

        assertFalse(peter2.equals(peter1));
    }

    @Test
    public void equals_shouldNotBeEqualWhenNotSameMarks() {
        String name1 = "Peter Petrov";
        List<Integer> marks1 = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter1 = new Student<>(name1, marks1);

        String name2 = "Peter Petrov";
        List<Integer> marks2 = List.of(5, 4, 3, 4, 5);
        Student<Integer> peter2 = new Student<>(name2, marks2);

        assertFalse(peter2.equals(peter1));
    }

    @Test
    public void equals_shouldNotBeEqualWhenNotStudent() {
        String name = "Peter Petrov";
        List<Integer> marks = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter = new Student<>(name, marks);

        assertFalse(peter.equals("Hello World"));
    }

    @Test
    public void hash_shouldHaveSameHashCodeWhenEqualObjects() {
        String name1 = "Peter Petrov";
        List<Integer> marks1 = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter1 = new Student<>(name1, marks1);

        String name2 = "Peter Petrov";
        List<Integer> marks2 = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter2 = new Student<>(name2, marks2);

        assertTrue(peter2.equals(peter1));
        assertEquals(peter2.hashCode(), peter1.hashCode());
    }

    @Test
    public void hash_shouldHaveDifferentHashCodeWhenNotEqualObjects() {
        String name1 = "Peter Petr";
        List<Integer> marks1 = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter1 = new Student<>(name1, marks1);

        String name2 = "Peter Petrov";
        List<Integer> marks2 = List.of(5, 4, 3, 4, 5, 2);
        Student<Integer> peter2 = new Student<>(name2, marks2);

        assertFalse(peter2.equals(peter1));
        assertNotEquals(peter2.hashCode(), peter1.hashCode());
    }
}
