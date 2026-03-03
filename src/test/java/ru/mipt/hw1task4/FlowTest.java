package ru.mipt.hw1task4;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FlowTest {

    @Test
    public void of_Null_Throws() {
        assertThrows(IllegalArgumentException.class, () -> Flow.of((List<Integer>) null));
        assertThrows(IllegalArgumentException.class, () -> Flow.of((Object) null));
        assertThrows(IllegalArgumentException.class, () -> Flow.of(null, null));
        assertThrows(IllegalArgumentException.class, () -> Flow.of(null, null, null));
    }

    @Test
    public void of_List() {
        Flow<Integer> flow = Flow.of(Arrays.asList(1, -2, 3));
        List<Integer> expected = List.of(1, -2, 3);
        assertEquals(expected, flow.toList());

        Flow<String> flowOfString = Flow.of(Arrays.asList("a", "ab", "abc"));
        List<String> expectedStrings = List.of("a", "ab", "abc");
        assertEquals(expectedStrings, flowOfString.toList());
    }

    @Test
    public void of_VarArgs() {
        Flow<Integer> f = Flow.of(5, 4, 0, -1, 99);
        List<Integer> expected = List.of(5, 4, 0, -1, 99);
        assertEquals(expected, f.toList());

        Flow<String> flowOfString = Flow.of("a", "ab", "abc");
        List<String> expectedStrings = List.of("a", "ab", "abc");
        assertEquals(expectedStrings, flowOfString.toList());
    }

    @Test
    public void of_Generator() {
        var flow = Flow.of(3, x -> x + 1, x -> x < 10);
        List<Integer> expected = List.of(3, 4, 5, 6, 7, 8, 9);
        assertEquals(expected, flow.toList());

        var flowStrings = Flow.of("a", x -> x + "a", x -> x.length() < 5);
        List<String> expectedStrings = List.of("a", "aa", "aaa", "aaaa");
        assertEquals(expectedStrings, flowStrings.toList());
    }

    @Test
    public void map_Once() {
        List<Integer> list = Arrays.asList(1, -2, 3);
        List<Integer> result = Flow.of(list)
                .map(x -> x * x)
                .toList();
        List<Integer> expected = List.of(1, 4, 9);
        assertEquals(expected, result);
    }

    @Test
    public void map_Multiple() {
        List<Integer> list = Arrays.asList(1, -2, 3);
        List<Integer> result = Flow.of(list)
                .map(x -> x * x)
                .map(x -> x / 2)
                .toList();
        List<Integer> expected = List.of(0, 2, 4);
        assertEquals(expected, result);
    }


    @Test
    public void filter() {
        List<Integer> list = Arrays.asList(1, -2, 3, -99);
        List<Integer> result = Flow.of(list).filter(x -> x > 0).toList();
        List<Integer> expected = List.of(1, 3);
        assertEquals(expected, result);
    }


    @Test
    public void map_filter() {
        List<String> list = Arrays.asList("a", "abc", "hello world");
        List<Integer> result = Flow.of(list)
                .map(String::length)
                .filter(n -> n < 10)
                .toList();
        List<Integer> expected = Arrays.asList(1, 3);
        assertEquals(expected, result);
    }

    @Test
    public void reduce_returnsNonEmptyOptional() {
        Optional<Integer> result = Flow.of(1, -2, 3)
                .reduce(Integer::sum);
        int expected = 2;
        assertEquals(expected, result.get());
    }

    @Test
    public void reduce_throws() {
        Optional<Integer> result = Flow.of(-1, -2, -3)
                .filter(x -> x > 0)
                .reduce(Integer::sum);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void collect_toList() {
        List<Integer> list = Arrays.asList(1, -2, 3, -99);
        List<Integer> result = Flow.of(list)
                .filter(x -> x > 0)
                .collect(ArrayList::new, List::add);
        List<Integer> expected = List.of(1, 3);
        assertEquals(expected, result);
    }

    @Test
    public void collect_toSet() {
        List<Integer> list = Arrays.asList(1, 3, 3, 1);
        Set<Integer> result = Flow.of(list).collect(HashSet::new, Set::add);
        Set<Integer> expected = Set.of(1, 3);
        assertEquals(expected, result);
    }

    @Test
    public void map_filter_reduce() {
        int result = Flow.of("ab", "abc", "hello world")
                .map(String::length)
                .filter((Integer x) -> x < 10)
                .reduce(Integer::sum)
                .get();
        int expected = 5;
        assertEquals(expected, result);
    }

    @Test
    public void map_filter_collect() {
        Set<Integer> result = Flow.of("ab", "abc", "hello world", "abc", "abc")
                .map(String::length)
                .filter((Integer x) -> x < 10)
                .collect(HashSet::new, HashSet::add);
        Set<Integer> expected = new HashSet<>(List.of(3, 2));
        assertEquals(expected, result);
    }
}
