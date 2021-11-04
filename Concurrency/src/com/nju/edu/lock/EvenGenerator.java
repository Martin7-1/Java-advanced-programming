package com.nju.edu.lock;

abstract class IntGenerator {
    private volatile boolean canceled = false;

    public abstract int next();

    public void cancel() {
        this.canceled = true;
    }

    public boolean isCanceled() {
        return this.canceled;
    }
}

public class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;

    @Override
    public int next() {
        // 这里会出现资源共享问题
        // 具体表现在某个线程在执行到第一句++的时候，另一个线程开始执行，就会导致值不再是偶数
        // 请避免如下的写法
        ++currentEvenValue;
        ++currentEvenValue;

        return currentEvenValue;
    }
}
