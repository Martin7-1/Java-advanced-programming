package com.nju.edu.concurrency;

public class LiftOff implements Runnable {

    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id +"(" + (countDown > 0 ? countDown : "LiftOff!") + "), ";
    }

    @Override
    public void run() {
        // runnable接口仅仅布置任务
        while (countDown-- > 0) {
            System.out.println(status());
            Thread.yield();
        }
    }
}