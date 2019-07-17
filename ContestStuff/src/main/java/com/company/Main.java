package com.company;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Main {

    public static boolean isLösungsWort(final String s1, final String s2) {
        final Supplier<int[]> supplier = () -> {
            return IntStream.generate(() -> 0).limit(Character.MAX_VALUE).toArray();
        };
        final ObjIntConsumer<int[]> accumulator = (t, value) -> {
            t[value]++;
        };
        final BiConsumer<int[], int[]> combiner = (t, u) -> {
        };
        return Arrays.equals(
                s1.chars().collect(supplier, accumulator, combiner),
                s2.chars().collect(supplier, accumulator, combiner));
    }

    public static void main(String[] args) {
        System.out.println(isLösungsWort("Katze", "Hund"));
        System.out.println(isLösungsWort("Katze", "Katze"));
        System.out.println(isLösungsWort("Lösungswort", "Lösungswort"));
        System.out.println(isLösungsWort("Lösungswort", "wortLösungs"));
    }
}
