package com.nju.edu.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

public class FileStreamDemoTest {

    private final String FILEPATH = "temp.log";
    File file = new File(FILEPATH);

    @Test
    public void writeTest() throws Exception {
        FileStreamDemo.write(FILEPATH);

        InputStream in = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        int len = in.read(bytes);

        assertEquals("Hello World\n", new String(bytes));
        assertEquals(12, len);

        in.close();
    }

    @Test
    public void readTest() throws Exception {
        String actual = FileStreamDemo.read(FILEPATH);
        String expect = "Hello World\n";
        
        assertEquals(expect, actual);
    }
    
}
