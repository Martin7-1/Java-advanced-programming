package com.nju.edu.generate;

import static java.util.stream.IntStream.*;

public class Ranges {

    public static void main(String[] args) {
        int result = 0;

        // 最普通的循环
        for (int i = 10; i < 20; i++) {
            result += i;
        }

        System.out.println("use normal loop, result = " + result);

        int rangeResult = 0;
        for (int i : range(10, 20).toArray()) {
            rangeResult += i;
        }

        System.out.println("apply stream to loop, rangeResult = " + rangeResult);

        // 或者我们还可以更简单一点
        // 使用流的好处
        System.out.println("the simple way to use IntStream.range() = " + range(10, 20).sum());
    }
    
}
