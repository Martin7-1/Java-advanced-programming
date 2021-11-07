package com.nju.edu.operation;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class FunctionMap {
    static String[] elements = {"12", "23", "45"};

    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }

    static void test(String description, Function<String, String> func) {
        System.out.println(" ---( " + description + " )--- ");
        testStream().map(func).forEach(System.out::println);
    }

    public static void main(String[] args) {
        test("add brackets", s -> "[" + s + "]");

        test("Increment", s -> {
            try {
                return Integer.parseInt(s) + 1 + "";
            } catch (NumberFormatException e) {
                return s;
            }
        });

        test("replace", s -> s.replace("2", "9"));

        test("Take last digit", s -> s.length() > 0 ? s.charAt(s.length() - 1) + "" : s);
    }
}
