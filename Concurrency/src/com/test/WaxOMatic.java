package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.nju.edu.car.Car;
import com.nju.edu.car.WaxOff;
import com.nju.edu.car.WaxOn;

public class WaxOMatic {

    public static void main(String[] args) {
        Car car = new Car();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new WaxOn(car, "A"));
        executorService.execute(new WaxOn(car, "B"));
        executorService.execute(new WaxOff(car, "C"));
        executorService.execute(new WaxOff(car, "D"));

        executorService.shutdown();
    }
    
}
