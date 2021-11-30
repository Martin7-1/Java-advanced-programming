package com.nju.edu.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStreamDemo {

    private static final String FILEPATH = "temp.log";

    public static void main(String[] args) throws Exception {
        write(FILEPATH);
        read(FILEPATH);
    }

    public static void write(String filepath) throws Exception {
        // 通过File类找到一个文件
        File file = new File(filepath);

        // 通过子类实例化父类对象
        OutputStream out = new FileOutputStream(file);
        // 实例化时，默认为覆盖原文件内容方式，如果添加true参数，则变为对原文件追加内容的方式
        // OutputStream out = new FileOutputStream(file, true);

        // 进行写操作
        String str = "Hello World\n";
        byte[] bytes = str.getBytes();
        out.write(bytes);

        // 关闭输出流
        out.close();
    }

    public static void read(String filepath) throws Exception {
        // 同上
        File file = new File(filepath);

        InputStream in = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        int len = in.read(bytes);
        System.out.println("读入数据的长度: " + len);

        in.close();
        // 这里用String.valueof和new String结果会不一样
        System.out.println("内容为: \n" + new String(bytes));
    }
    
}
