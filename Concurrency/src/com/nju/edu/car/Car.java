package com.nju.edu.car;

public class Car {

    private boolean isWaxOn = false;

    public synchronized void wax(String workerName) throws InterruptedException {
        while (isWaxOn) {
            wait();
        }
        // 准备好抛光
        isWaxOn = true;
        System.out.println(workerName + " is waxing on!");
        notifyAll();
    }

    public synchronized void buff(String workerName) throws InterruptedException {
        while (!isWaxOn) {
            wait();
        }
        // 准备好打蜡
        isWaxOn = false;
        System.out.println(workerName + " is buffing!");
        notifyAll();
    }
    
}
