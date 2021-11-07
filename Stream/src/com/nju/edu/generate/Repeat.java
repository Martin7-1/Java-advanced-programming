package com.nju.edu.generate;

import java.util.stream.IntStream;

public class Repeat {

    public static void repeat(int n, Runnable action) {
        IntStream.range(0, n).forEach(i -> action.run());
    }
}