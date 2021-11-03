package com.nju.edu.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExceptionTest {

    public void throwRuntimeException() {
        // 直接抛出一个异常
        // 这里抛出的是RuntimeException，不需要在方法处声明
        throw new ArithmeticException();
    }

    public void runWithoutCatch() throws InterruptedException {
        // 抛出一个非RuntimeException的异常
        // 需要在方法上声明抛出了这个异常，以便调用者能够捕获
        throw new InterruptedException();
    }

    public void runWithCatch() {
        // 用try-catch来捕获异常
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String str = in.readLine();
        } catch (IOException e) {
            System.out.println("catch IOException successfully");
        }
    }

    public static void main(String[] args) {
        ExceptionTest test = new ExceptionTest();
        try {
            // 如果这句有异常抛出，那么接下来的语句都不会执行
            test.throwRuntimeException();
        } catch (RuntimeException e) {
            // 实际上我们不需要捕获，因为JVM会帮我们捕获运行时异常
            System.out.println("catch RuntimeException successfully!");
        }

    }
    
}
