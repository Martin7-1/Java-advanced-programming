package com.nju.edu.terminal;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Pair {
    public final Character c;
    public final Integer i;

    Pair(Character c, Integer i) {
        this.c = c;
        this.i = i;
    }

    public Character getC() {
        return this.c;
    }

    public Integer getI() {
        return this.i;
    }

    @Override
    public String toString() {
        return "Pair(" + c + ", " + i + ")";
    }
}

class RandomPair {
    Random rand = new Random(47);
    // an infinite iterator of random capital letters
    Iterator<Character> capChars = rand.ints(65, 91)
            .mapToObj(i -> (char)i)
            .iterator();
    
    public Stream<Pair> stream() {
        return rand.ints(100, 1000).distinct()
                .mapToObj(i -> new Pair(capChars.next(), i));
    }
}

public class MapCollector {
    
    public static void main(String[] args) {
        Map<Integer, Character> map = new RandomPair().stream().limit(8)
                    .collect(Collectors.toMap(Pair::getI, Pair::getC));
        
        System.out.println(map);
    }
}
