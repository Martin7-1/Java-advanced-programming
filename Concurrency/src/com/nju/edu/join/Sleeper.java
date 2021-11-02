package com.nju.edu.join;

public class Sleeper extends Thread {

    private int duration;

    public Sleeper(String name, int sleepTime) {
        super(name);
        this.duration = sleepTime;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted.");
            return;
        }

        System.out.println(getName() + " has awakened.");
    }
    
}
