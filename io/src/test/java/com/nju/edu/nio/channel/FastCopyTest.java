package com.nju.edu.nio.channel;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

public class FastCopyTest {

    final String src = "channelSrc.txt";
    final String dest = "channelDest.txt";

    @Test
    public void fastCopyTest() throws IOException {
        // 读取src文件和dest文件比较是否相同
        Scanner srcScanner = null;
        Scanner destScanner = null;
        String srcContent = "";
        String destContent = "";

        FastCopy.fastCopy(src, dest);

        try {
            srcScanner = new Scanner(new BufferedReader(new FileReader(src)));
            destScanner = new Scanner(new BufferedReader(new FileReader(dest)));

            while (srcScanner.hasNextLine()) {
                srcContent += srcScanner.nextLine();
            }
            while (destScanner.hasNextLine()) {
                destContent += destScanner.nextLine();
            }
        } finally {
            if (srcScanner != null) {
                srcScanner.close();
            }
            if (destScanner != null) {
                destScanner.close();
            }
        }

        assertEquals(srcContent, destContent);
    }
    
}
