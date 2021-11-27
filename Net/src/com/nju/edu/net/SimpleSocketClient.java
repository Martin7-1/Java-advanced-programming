package com.nju.edu.net;

import java.net.Socket;

public class SimpleSocketClient {
    
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: SimpleSocketClient <server> <path>");
            System.exit(0);
        }

        String server = args[0];
        String path = args[1];

        System.out.println("Loading contents of URL: " + server);

        try {
            Socket socket = new Socket(server, 80);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
