package com.nju.edu;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MainTest {

    @Test
    public void sayHelloTest() {
        Main main = new Main();
        assertEquals("Hello World", main.sayHello());
    }

}
