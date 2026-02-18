package ru.mipt.hw1task3;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Represents a Student with name and marks of type T
 */

public class Student<T> {
    private String name;
    private List<T> marks;
    private final Predicate<T> markValidityRule;
    private final List<Consumer<Student<T>>> prevOps;

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
        this.prevOps = new ArrayList<>();
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
     * Sets this student's name to a given one.
     *
     * @param name
     */
    public void setName(String name) {
        String oldName = this.name;
        prevOps.add((student) -> student.name = oldName);
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
        prevOps.add((student) -> student.marks.remove(marks.size() - 1));
        marks.add(mark);
    }

    /**
     * Removes last mark with provided value. If no such mark do nothing.
     *
     * @param value
     */
    public void removeMark(T value) {
        int index = marks.lastIndexOf(value);
        if (index != -1) {
            prevOps.add((student) -> student.marks.add(index, value));
            marks.remove(index);
        }
    }

    /**
     * Returns a list with all marks obtained by this student
     *
     * @return list with all marks
     */
    public List<T> getMarks() {
        return new ArrayList<>(marks);
    }

    /**
     * Undoes the last action of setting name or adding/removing a mark
     */
    public void undo() {
        if (prevOps.isEmpty()) return;

        Consumer<Student<T>> lastOperation = prevOps.remove(prevOps.size() - 1);
        lastOperation.accept(this);
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
