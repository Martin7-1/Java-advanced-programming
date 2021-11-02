package com.nju.edu.car;

import java.util.concurrent.TimeUnit;

public class WaxOff implements Runnable {

    private Car car;
    private String name;

    public WaxOff(Car car, String name) {
        this.car = car;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.buff(name);
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException e) {
            System.out.println("Task end via interrupted");
        }
        System.out.println("Ending wax off task");
    }
    
}
