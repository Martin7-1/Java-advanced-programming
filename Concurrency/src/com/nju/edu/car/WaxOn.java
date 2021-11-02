package com.nju.edu.car;

import java.util.concurrent.TimeUnit;

public class WaxOn implements Runnable {
    
    private Car car;
    private String name;

    public WaxOn(Car car, String name) {
        this.car = car;
        this.name = name;
    }
    
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.wax(name);
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException e) {
            System.out.println("Task end via Interrupted");
        }
        System.out.println("Ending wax on task");
    }
}
