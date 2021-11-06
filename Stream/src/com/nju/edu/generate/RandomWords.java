package com.nju.edu.generate;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class RandomWords implements Supplier<String> {
    List<String> words = new ArrayList<>();
    Random rand = new Random(47);

    public RandomWords(String fname) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fname));

        // 略过第一行
        // 第一行是注释行
        for (String line : lines.subList(1, lines.size())) {
            for (String word : line.split("[ .?,]+")) {
                words.add(word.toLowerCase());
            }
        }
    }

    public String get() {
        return words.get(rand.nextInt(words.size()));
    }

    @Override
    public String toString() {
        return words.stream().collect(Collectors.joining(" "));
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Stream.generate(new RandomWords("Cheese.txt")).limit(10).collect(Collectors.joining(" ")));
    }
}
