package com.nju.edu.concurrency;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("做一些耗时的任务...");
        // sleep()方法参数为毫秒
        Thread.sleep(5000);

        return "OK";
    }
    
}
