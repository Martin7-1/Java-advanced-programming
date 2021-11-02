package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.nju.edu.concurrency.LiftOff;

public class FixedThreadPool {

    public static final int THREAD_AMOUNT = 5;

    public static void main(String[] args) {
        // 创建固定线程数的线程池
        // 任何时候都只有固定的线程数被创建
        // 如果所有线程都在活动状态时有其他任务提交，会在等待队列中直到有线程可用
        ExecutorService exec = Executors.newFixedThreadPool(THREAD_AMOUNT);

        for (int i = 0; i < THREAD_AMOUNT; i++) {
            exec.execute(new LiftOff(10));
        }
        exec.shutdown();
    }
    
}
