package com.nju.edu.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CachedThreadPool {
    
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
            .mapToObj(InterferingTask::new)
            .forEach(exec::execute);
            exec.shutdownNow();
    }
}
