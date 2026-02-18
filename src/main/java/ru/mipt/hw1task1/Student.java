package ru.mipt.hw1task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Student with name and marks
 */

public class Student {
    private String name;
    private List<Integer> marks = new ArrayList<>();

    public Student(String name) {
        this(name, List.of());
    }

    public Student(String name, List<Integer> marks) {
        this.name = name;
        this.marks = new ArrayList<>(marks);
    }

    /**
     * Returns this student's name
     *
     * @return student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets this student's name to a given one
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a given mark to this student's marks
     *
     * @param mark
     */
    public void addMark(int mark) {
        marks.add(mark);
    }

    /**
     * Removes last mark having provided value. If no such mark do nothing.
     *
     * @param value
     */
    public void removeMark(int value) {
        int index = marks.lastIndexOf(value);
        if (index != -1) {
            marks.remove(index);
        }
    }

    /**
     * Returns a list with all marks obtained by this student
     *
     * @return list with all marks
     */
    public List<Integer> getMarks() {
        return new ArrayList<>(marks);
    }

    @Override
    public String toString() {
        return name + ": " + marks;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student student)) return false;
        return Objects.equals(name, student.name) && Objects.equals(marks, student.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, marks);
    }
}
