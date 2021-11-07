package com.nju.edu.iterate;

import java.util.stream.Stream;

public class Fibonacci {

    int x = 1;

    public Stream<Integer> iterateNumber() {
        return Stream.iterate(0, i -> {
            int result = x + i;
            x = i;
            return result;
        });
    }

    public static void main(String[] args) {
        new Fibonacci().iterateNumber()
                        .skip(20) // 跳过前20个
                        .limit(10) // 将数量限制在10个
                        .forEach(System.out::println);
    }
    
}
