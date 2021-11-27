package com.nju.edu.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class URLReader {

    public static void main(String[] args) throws Exception {

        URL url = new URL("https://www.nju.edu.cn");
        // 读取html内容
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }

        in.close();
    }
}