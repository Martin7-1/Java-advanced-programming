package com.nju.edu.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyBytes {

    public static void copy() throws Exception {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(new File("file.txt"));
            out = new FileOutputStream(new File("newFile.txt"));

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) {
                // close the stream
                in.close();
            }
            if (out != null) {
                // close the stream
                out.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        copy();
    }
    
}
