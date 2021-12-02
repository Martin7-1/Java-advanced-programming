package com.nju.edu.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FastCopy {
    
    public static void fastCopy(String src, String dest) throws IOException {
        // 获得源文件的输入字节流
        FileInputStream fin = new FileInputStream(src);
        // 获得输入字节流的文件通道
        FileChannel fcin = fin.getChannel();

        // 获得目标文件的输出字节流
        FileOutputStream fout = new FileOutputStream(dest);
        // 获得目标字节流的文件通道
        FileChannel fcout = fout.getChannel();

        // 缓冲区，分配1024个字符
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        try {
            while (true) {

                // 从源文件中读取数据放到缓存区中
                int c = fcin.read(buffer);

                // c = -1时表示EOF
                if (c == -1) {
                    break;
                }

                // 切换读写
                buffer.flip();

                fcout.write(buffer);

                // 清空缓冲区
                buffer.clear();
            }
        } finally {
            if (fin != null) {
                fin.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
}
