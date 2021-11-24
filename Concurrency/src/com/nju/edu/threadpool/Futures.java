package com.nju.edu.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Futures {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<Integer> f = exec.submit(new CountingTask(99));
        // 如果任务尚未完成，f.get()的调用会阻塞直到结果产生
        System.out.println(f.get());

        exec.shutdown();
    }
    
}
