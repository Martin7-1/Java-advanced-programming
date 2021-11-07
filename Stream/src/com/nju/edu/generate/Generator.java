package com.nju.edu.generate;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Generator implements Supplier<String> {
    // 设定seed以保证每次运行有相同的答案
    Random rand = new Random(47);
    char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    
    public String get() {
        return "" + letters[rand.nextInt(letters.length)];
    }
    
    public static void main(String[] args) {
        String word = Stream.generate(new Generator())
                            .limit(30)
                            .collect(Collectors.joining());
        System.out.println(word);
    }
}
