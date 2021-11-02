package com.nju.edu.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NaiveExceptionHandler {
    public static void main(String[] args) {
        // 常规的异常处理方式
        // 在多线程中没有用处

        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread());
            exec.shutdown();
        } catch (RuntimeException e) {
            // 这句永远都执行不到
            System.out.println("Exception has been handled!");
        }
    }
    
}
