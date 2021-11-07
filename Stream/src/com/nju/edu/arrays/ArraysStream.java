package com.nju.edu.arrays;

import java.util.Arrays;

public class ArraysStream {

    public static void main(String[] args) {
        Arrays.stream(new double[] {
            3.14159, 2.718, 1.618
        }).forEach(n -> System.out.printf("%f ", n));

        System.out.println();

        Arrays.stream(new int[] {
            1, 3, 5, 7, 9, 11
        }).forEach(n -> System.out.printf("%d ", n));

        System.out.println();

        // 选择一个子数组进行输出
        // 这里选择了[3, 6)的子数组进行输出，注意左闭右开
        Arrays.stream(new int[] {
            1, 5, 9, 56, 32, 22, 95, 65
        }, 3, 6).forEach(n -> System.out.printf("%d ", n));
    }
    
}
