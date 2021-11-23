package com.nju.edu.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SingleThreadExecutor2 {
    
    public static void main(String[] args)throws InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        
        IntStream.range(0, 10)
            .mapToObj(InterferingTask::new)
            .forEach(exec::execute);
        
        exec.shutdown();
    }
}
