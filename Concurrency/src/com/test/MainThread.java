package com.test;

import com.nju.edu.concurrency.LiftOff;

public class MainThread {

    public static final int THREAD_AMOUNT = 5;
    
    public static void main(String[] args) {
        
        // 创建多线程
        for (int i = 0; i < THREAD_AMOUNT; i++) {
            new Thread(new LiftOff(10)).start();
        }
        System.out.println("Waiting for LiftOff");
    }
}