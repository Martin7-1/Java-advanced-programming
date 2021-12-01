package com.nju.edu.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class FileReadWriteDemo {

    private static final String FILEPATH = "tempTest.log";

    public static void main(String[] args) throws IOException {
        write(FILEPATH, "Hello World!!!\r\n");
        System.out.println("内容为: " + new String(read(FILEPATH)));
    }

    public static void write(String filepath, String str) throws IOException {
        // 1. 使用File类绑定一个File对象
        File file = new File(FILEPATH);

        // 2. 把File对象绑定到流对象上
        Writer out = new FileWriter(file);
        // Writer out = new FileWriter(file, true), 追加内容的方式

        // 3. 进行读或写操作
        out.write(str);

        // 4.关闭流
        // 字符流操作时使用了缓冲区，并在关闭字符流时会强制将缓冲区内容输出
        // 如果不关闭流，则缓冲区的内容是无法输出的
        // 如果想在不关闭流时，将缓冲区内容输出，可以使用 flush 强制清空缓冲区
        out.flush();
        out.close();
    }

    public static char[] read(String filepath) throws IOException {
        // 同上
        File file = new File(FILEPATH);

        // 同上
        Reader in = new FileReader(file);

        // 进行读操作
        int temp = 0;
        int len = 0;
        char[] c = new char[1024];

        while ((temp = in.read()) != -1) {
            c[len] = (char)temp;
            len++;
        }

        System.out.println("文件字符数为: " + len);

        in.close();

        return c;
    }
    
}
