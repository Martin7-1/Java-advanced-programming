package com.nju.edu.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask implements Runnable {

    private int countDown = 10;

    @Override
    public void run() {
        // TimeUnit.MILLISECONDS.sleep()方法可能会抛出异常
        // Thread.sleep()方法是过去使用的，现在都用上面这个方法
        try {
            while (countDown-- > 0) {
                // Old-style: Thread.sleep(100);
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(countDown);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted!");
        }
    }

    public static final int THREAD_AMOUNT = 5;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("before sleeping...");

        for (int i = 0; i < THREAD_AMOUNT; i++) {
            executorService.execute(new SleepingTask());
        }

        executorService.shutdown();
    }
    
}
