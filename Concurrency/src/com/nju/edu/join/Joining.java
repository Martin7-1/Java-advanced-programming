package com.nju.edu.join;

import java.util.concurrent.TimeUnit;

public class Joining {

    public static void main(String[] args) {
        Sleeper sleepy = new Sleeper("Sleepy", 1500);
        Sleeper grumpy = new Sleeper("Grumpy", 1500);
        Joiner doepy = new Joiner("Doepy", sleepy);
        Joiner doc = new Joiner("Doc", grumpy);

        try {
            // 主线程睡500ms再来打断grumpy线程
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        grumpy.interrupt();

        // output:
        // Grumpy waa interrupted
        // Doc join completed
        // Sleepy has awakened
        // Doepy join completed

        // analyse:
        // grumpy.interrupt()打断了正在执行sleep()的grumpy线程，导致其死去
        // doc线程时join了grumpy线程的，在grumpy线程死去后doc线程开始执行
        // doepy线程同理等到sleepy线程死去才会开始执行(因为doepy线程join了sleepy线程)
        // 总结:
        // join()让线程之间的运行由并行变成了串行，即某个线程会等到另一个线程死去再开始执行，通过join()方法
    }
    
}
