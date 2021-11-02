package com.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.nju.edu.concurrency.MyCallable;

public class FutureSimpleDemo {
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 提交任务
        Future<String> future = executorService.submit(new MyCallable());

        System.out.println("do something...");
        try {
            // future.get()会堵塞直到Callable的任务做完并且返回结果
            System.out.println("得到异步任务返回结果: " + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Completed!");
    }
}
