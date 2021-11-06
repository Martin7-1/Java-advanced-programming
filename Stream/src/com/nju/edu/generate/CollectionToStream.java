package com.nju.edu.generate;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.*;

public class CollectionToStream {

    public static void main(String[] args) {
        List<Bubble> bubbles = Arrays.asList(new Bubble(1), new Bubble(2), new Bubble(3));
        System.out.println(bubbles.stream()
            .mapToInt(b -> b.i)
            .sum());

        Set<String> set = new HashSet<>(Arrays.asList("It's a wonderful day for pies!".split(" ")));
        set.stream().map(x -> x + " ").forEach(System.out::print);
        System.out.println();

        Map<String, Double> map = new HashMap<>();
        map.put("pi", 3.14159);
        map.put("e", 2.718);
        map.put("phi", 1.618);
        map.entrySet().stream()
            .map(e -> e.getKey() + ": " + e.getValue())
            .forEach(System.out::println);
    }
    
}
