package com.nju.edu.scan;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class ScanTest {

    String filepath = "scanTest.txt";

    @Test
    public void scanTest() throws IOException {
        ScanDemo s = new ScanDemo();
        String actual = s.scan(filepath);
        String expect = "Hello Java IO";

        assertEquals(expect, actual);
    }
    
}
