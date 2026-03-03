package ru.mipt.hw1task4;

import java.util.*;
import java.util.function.*;

public class Flow<T> {
    private final Supplier<List<T>> dataSupplier;

    private Flow(Supplier<List<T>> supplier) {
        this.dataSupplier = supplier;
    }

    public static <T> Flow<T> of(List<T> list) {
        if (list == null || list.contains(null)) throw new IllegalArgumentException("The provided list is null");
        return new Flow<>(() -> new ArrayList<>(list));
    }

    @SafeVarargs
    public static <T> Flow<T> of(T... args) {
        return Flow.of(Arrays.asList(args));
    }

    public static <T> Flow<T> of(T seed, Function<T, T> next, Predicate<T> hasNext) {
        if ((seed == null) || (next == null) || (hasNext == null))
            throw new IllegalArgumentException("Provided null argument.");

        Supplier<List<T>> supplier = () -> {
            List<T> result = new ArrayList<>();
            T val = seed;
            while (hasNext.test(val)) {
                result.add(val);
                val = next.apply(val);
            }
            return result;
        };
        return new Flow<>(supplier);
    }

    public <R> Flow<R> map(Function<T, R> function) {
        Supplier<List<R>> supplier = () -> {
            List<R> result = new ArrayList<>();
            for (T el : dataSupplier.get()) {
                result.add(function.apply(el));
            }
            return result;
        };
        return new Flow<R>(supplier);
    }

    public Flow<T> filter(Predicate<T> predicate) {
        Supplier<List<T>> supplier = () -> {
            List<T> result = new ArrayList<>();
            for (var el : dataSupplier.get()) {
                if (predicate.test(el)) {
                    result.add(el);
                }
            }
            return result;
        };
        return new Flow<>(supplier);
    }

    public Optional<T> reduce(BinaryOperator<T> operator) {
        List<T> data = dataSupplier.get();
        if (data.isEmpty()) return Optional.empty();
        T accumulator = data.get(0);
        for (int i = 1; i < data.size(); i++) {
            accumulator = operator.apply(accumulator, data.get(i));
        }
        return Optional.of(accumulator);
    }

    public <A> A collect(Supplier<A> supplier, BiConsumer<A, T> combiner) {
        A accumulator = supplier.get();
        for (T el : dataSupplier.get()) {
            combiner.accept(accumulator, el);
        }
        return accumulator;
    }

    public List<T> toList() {
        return new ArrayList<>(dataSupplier.get());
    }
}
