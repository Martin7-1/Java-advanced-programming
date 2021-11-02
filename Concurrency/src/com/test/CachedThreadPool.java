package com.test;

import java.util.concurrent.Executors;

import com.nju.edu.concurrency.LiftOff;

import java.util.concurrent.ExecutorService;

public class CachedThreadPool {

    public static final int THREAD_AMOUNT = 3;

    public static void main(String[] args) {
        // 根据需要创建线程
        // 如果现有线程没有可用的，就创建一个新线程并添加到池中
        // 如果有被使用完但还没有被销毁的，就复用该线程
        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < THREAD_AMOUNT; i++) {
            // 根据需要创建新线程的线程池
            exec.execute(new LiftOff(10));
        }
        exec.shutdown();

    }
    
}
