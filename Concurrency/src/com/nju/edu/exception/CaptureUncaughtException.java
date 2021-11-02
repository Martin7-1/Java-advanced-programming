package com.nju.edu.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class HandlerThreadFactory implements ThreadFactory {
    
    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " creating new Thread.");
        Thread t = new Thread(r);

        System.out.println("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("eh = " + t.getUncaughtExceptionHandler());

        return t;
    }
}

public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new AnotherExceptionThread());
        exec.shutdown();
    }
    
}
