package ru.mipt.hw1task3;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    @Test
    public void undo_setName_shouldUndoSingleTime() {
        String name = "Ivan Ivanov";
        Student<String> ivan = new Student<>(name);
        assertEquals(name, ivan.getName());
        String newName = "Ivanushka";
        ivan.setName(newName);
        assertEquals(newName, ivan.getName());
        ivan.undo();
        assertEquals(name, ivan.getName());
    }

    @Test
    public void undo_setName_shouldUndoMultipleTimes() {
        String initialName = "Ivan Ivanov";
        Student<String> ivan = new Student<>(initialName);
        String newName = "Ivanushka";
        ivan.setName(newName);
        String newName2 = "Ivanushka 2";
        ivan.setName(newName2);
        ivan.undo();
        ivan.undo();
        assertEquals(initialName, ivan.getName());
    }

    @Test
    public void undo_setName_shouldUndoTilInitial() {
        String initialName = "Ivan Ivanov";
        Student<String> ivan = new Student<>(initialName);
        String newName = "Ivanushka";
        ivan.setName(newName);
        String newName2 = "Ivanushka 2";
        ivan.setName(newName2);
        ivan.undo();
        ivan.undo();
        ivan.undo();
        ivan.undo();
        ivan.undo();
        assertEquals(initialName, ivan.getName());
    }

    @Test
    public void undo_removeMark_shouldUndoSingleTime() {
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of(5, 4, 3, 4, 5);
        Student<Integer> peter = new Student<>(name, initialMarks);
        peter.removeMark(3);
        List<Integer> expectedMarksInt = List.of(5, 4, 4, 5);
        assertEquals(expectedMarksInt, peter.getMarks());
        peter.undo();
        assertEquals(initialMarks, peter.getMarks());
    }

    @Test
    public void undo_removeMark_shouldUndoMultipleTimes() {
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of(5, 4, 3);
        Student<Integer> peter = new Student<>(name, initialMarks);
        peter.removeMark(4);
        peter.removeMark(3);
        peter.removeMark(5);
        List<Integer> expectedMarksInt = List.of();
        assertEquals(expectedMarksInt, peter.getMarks());
        peter.undo();
        peter.undo();
        peter.undo();
        assertEquals(initialMarks, peter.getMarks());
    }

    @Test
    public void undo_removeMark_shouldUndoTilInitial() {
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of(5, 4, 3);
        Student<Integer> peter = new Student<>(name, initialMarks);
        peter.removeMark(4);
        peter.removeMark(3);
        peter.removeMark(5);
        List<Integer> expectedMarksInt = List.of();
        assertEquals(expectedMarksInt, peter.getMarks());
        peter.undo();
        peter.undo();
        peter.undo();
        peter.undo();
        peter.undo();
        peter.undo();
        assertEquals(initialMarks, peter.getMarks());
    }

    @Test
    public void undo_addMark_shouldUndoSingleTime() {
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of(5, 4, 3, 4, 5);
        Student<Integer> peter = new Student<>(name, initialMarks);
        peter.addMark(2);
        List<Integer> expectedMarksInt = List.of(5, 4, 3, 4, 5, 2);
        assertEquals(expectedMarksInt, peter.getMarks());
        peter.undo();
        assertEquals(initialMarks, peter.getMarks());
    }

    @Test
    public void undo_addMark_shouldUndoMultipleTimes() {
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of(5, 4, 3);
        Student<Integer> peter = new Student<>(name, initialMarks);
        peter.addMark(2);
        peter.addMark(3);
        peter.addMark(5);
        List<Integer> expectedMarksInt = List.of(5, 4, 3, 2, 3, 5);
        assertEquals(expectedMarksInt, peter.getMarks());
        peter.undo();
        peter.undo();
        peter.undo();
        assertEquals(initialMarks, peter.getMarks());
    }

    @Test
    public void undo_addMark_shouldUndoTilInitial() {
        String name = "Peter Petrov";
        List<Integer> initialMarks = List.of(5, 4, 3);
        Student<Integer> peter = new Student<>(name, initialMarks);
        peter.addMark(2);
        peter.addMark(3);
        peter.addMark(5);
        List<Integer> expectedMarksInt = List.of(5, 4, 3, 2, 3, 5);
        assertEquals(expectedMarksInt, peter.getMarks());
        peter.undo();
        peter.undo();
        peter.undo();
        peter.undo();
        peter.undo();
        assertEquals(initialMarks, peter.getMarks());
    }

    @Test
    public void undo_mixture_shouldUndoTilInitial() {
        String name = "Svetlana Ivanova";
        List<Integer> initialMarks = List.of(5, 4, 3);
        Student<Integer> sveta = new Student<>(name, initialMarks);
        sveta.addMark(2);
        sveta.addMark(3);
        sveta.setName("Sveta Petrova");
        sveta.addMark(5);
        sveta.setName("Sveta Nikolaeva");
        List<Integer> expectedMarksInt = List.of(5, 4, 3, 2, 3, 5);
        assertEquals(expectedMarksInt, sveta.getMarks());
        sveta.undo();
        sveta.undo();
        sveta.undo();
        sveta.undo();
        sveta.undo();
        assertEquals(initialMarks, sveta.getMarks());
        assertEquals(name, sveta.getName());
    }
}
