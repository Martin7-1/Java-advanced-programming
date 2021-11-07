package com.nju.edu.generate;

public class Looping {

    public static void hi() {
        System.out.println("Hi!");
    }

    public static void main(String[] args) {
        Repeat.repeat(3, () -> System.out.println("Looping!"));
        Repeat.repeat(3, Looping::hi);
    }
    
}
