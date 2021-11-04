package com.nju.edu.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenGenerator extends IntGenerator {

    private int currentEvenValue = 0;
    private Lock lock = new ReentrantLock();
    
    @Override
    public int next() {
        // 最简单的lock
        lock.lock();

        try {
            ++currentEvenValue;
            Thread.yield();
            ++currentEvenValue;

            return currentEvenValue;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EvenChecker.test(new MutexEvenGenerator(), 10);
    }
}
