package com.nju.edu.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class FileReadWriteDemoTest {

    String filepath = "tempTest.log";

    @Test
    public void writeTest() throws IOException {
        FileReadWriteDemo.write(filepath, "Hello NJU");

        File file = new File(filepath);
        FileReader fileReader = new FileReader(file);
        int temp;
        StringBuilder str = new StringBuilder();

        while ((temp = fileReader.read()) != -1) {
            str.append((char)temp);
        }

        fileReader.close();
        assertEquals("Hello NJU", str.toString());
    }

    @Test
    public void readTest() throws IOException {
        char[] expect = new char[1024];
        String str = "I am a student of NJU\r\n";
        FileReadWriteDemo.write(filepath, str);

        for (int i = 0; i < str.length(); i++) {
            expect[i] = str.charAt(i);
        }

        char[] actual = FileReadWriteDemo.read(filepath);
        assertEquals(new String(expect), new String(actual));
    }
    
}
