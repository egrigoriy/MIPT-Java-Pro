package ru.mipt.hw1task2;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Represents a Student with name and marks of type T
 */

public class Student<T> {
    private String name;
    private final List<T> marks;
    private final Predicate<T> markValidityRule;

    public Student(String name) {
        this(name, List.of());
    }

    public Student(String name, List<T> marks) {
        this(name, marks, mark -> true);
    }

    public Student(String name, List<T> marks, Predicate<T> markValidityRule) {
        this.name = name;
        this.marks = new ArrayList<T>(marks);
        if (!marks.stream().allMatch(markValidityRule)) {
            throw new IllegalArgumentException("Provided list of marks contains invalid values:" + marks);
        }
        this.markValidityRule = markValidityRule;
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
     * Adds a given mark to this student's marks.
     * Throws InvalidParameterException if the mark doesn't conform to the rule.
     *
     * @param mark
     * @throws InvalidParameterException
     */
    public void addMark(T mark) throws InvalidParameterException {
        if (!markValidityRule.test(mark)) {
            throw new IllegalArgumentException("The given mark " + mark + " is invalid!");
        }
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
    public List<T> getMarks() {
        return marks;
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
