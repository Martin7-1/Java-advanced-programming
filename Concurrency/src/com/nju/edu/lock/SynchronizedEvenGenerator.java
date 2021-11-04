package com.nju.edu.lock;

public class SynchronizedEvenGenerator extends IntGenerator {

    private int currentEvenValue;

    @Override
    public synchronized int next() {
        ++this.currentEvenValue;
        Thread.yield();
        ++this.currentEvenValue;

        return this.currentEvenValue;
    }

    public static void main(String[] args) {
        new EvenChecker(new SynchronizedEvenGenerator(), 10);
    }
}
