package com.nju.edu.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
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
            // connect to the server
            Socket socket = new Socket(server, 80);

            // create input and output streams to read from and write to the server
            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Follow data from the server until we finish reading the document
            String readLine;
            while ((readLine = in.readLine()) != null) {
                System.out.println(readLine);
            }

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
