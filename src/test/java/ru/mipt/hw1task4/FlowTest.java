package ru.mipt.hw1task4;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FlowTest {

    @Test
    public void of_List() {
        List<Integer> list = Arrays.asList(1, -2, 3);
        Flow<Integer> f = Flow.of(list);
        List<Integer> expected = List.of(1, -2, 3);
        assertEquals(expected, f.toList());
    }

    @Test
    public void of_VarArgs() {
        Flow f = Flow.of(5, 4, 0, -1, 99);
    }

    @Test
    public void of_Generator() {
        var f = Flow.of(3, x -> x + 1, x -> x < 10);
        List<Integer> expected = List.of(3, 4, 5, 6, 7, 8, 9);
        assertEquals(expected, f.toList());
    }

    @Test
    public void map() {
        List<Integer> list = Arrays.asList(1, -2, 3);
        List<Integer> result = Flow.of(list).map(x -> x * x).toList();
        List<Integer> expected = List.of(1, 4, 9);
        assertEquals(expected, result);
    }

    @Test
    public void mapNullThrow() {
        List<Integer> list = Arrays.asList(1, -2, 3, null);
        Function<Integer, Integer> f = x -> x * x;
        assertThrows(NullPointerException.class, () -> Flow.of(list).map(f).toList());
    }

    @Test
    public void filter() {
        List<Integer> list = Arrays.asList(1, -2, 3, -99);
        List<Integer> result = Flow.of(list).filter(x -> x > 0).toList();
        List<Integer> expected = List.of(1, 3);
        assertEquals(expected, result);
    }

    @Test
    public void filterFindNull() {
        List<Integer> list = Arrays.asList(1, null, 3, null);
        List<Integer> result = Flow.of(list).filter(Objects::isNull).toList();
        List<Integer> expected = Arrays.asList(null, null);
        assertEquals(expected, result);
    }

    @Test
    public void testMultiple() {
        int result = Flow.of(1, -2, 3).filter(x -> x > 0).reduce((x, y) -> x + y);
        int expected = 4;
        assertEquals(expected, result);
    }
}
