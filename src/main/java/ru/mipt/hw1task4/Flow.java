package ru.mipt.hw1task4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.*;

public class Flow<T> {
    private List<T> data;
    List<Runnable> operations = new ArrayList<>();

    private Flow(List<T> list) {
        if (list == null) throw new NullPointerException("Provided argument is null");
        data = new ArrayList<>(list);
    }

    public static <T> Flow<T> of(List<T> list) {
        return new Flow<>(list);
    }

    @SafeVarargs
    public static <T> Flow<T> of(T... args) {
        return Flow.of(Arrays.asList(args));
    }

    public static <T> Flow<T> of(T seed, Function<T, T> next, Predicate<T> hasNext) {
        List<T> list = new ArrayList<>();
        T val = seed;
        while(hasNext.test(val)) {
            list.add(val);
            val = next.apply(val);
        }
        return Flow.of(list);
    }

    public Flow<T> map(Function<T, T> function) {
        Runnable runnable = () -> {
            List<T> result = new ArrayList<>();
            for (T el : data) {
                result.add(function.apply(el));
            }
            data = result;
        };
        operations.add(runnable);
        return this;
    }

    public Flow<T> filter(Predicate<T> predicate) {
        Runnable runnable = () -> {
            List<T> result = new ArrayList<>();
            for (var el : data) {
                if (predicate.test(el)) {
                result.add(el);
            }
        }
        data = result;
    };
    operations.add(runnable);
    return this;
}

public T reduce(BinaryOperator<T> operator) {
    for (Runnable runnable : operations) {
        runnable.run();
    }
    T acc = data.get(0);
    for (int i = 1; i < data.size(); i++) {
        acc = operator.apply(acc, data.get(i));
    }
    return acc;
}

public <U, V> Collection<T> collect(Supplier<Collection<T>> supplier, BiConsumer<U, V> combiner) {
        Collection<T> result = supplier.get();
        for (T el : data) {

        }
        return result;
    }

    public List<T> toList() {
        for (Runnable runnable : operations) {
            runnable.run();
        }
        return new ArrayList<>(data);
    }
}
