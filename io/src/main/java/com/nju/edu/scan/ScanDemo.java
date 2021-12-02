package com.nju.edu.scan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ScanDemo {

    public String scan(String filepath) throws IOException {
        Scanner scanner = null;
        StringBuilder res = new StringBuilder();

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(filepath)));

            while (scanner.hasNextLine()) {
                res.append(scanner.nextLine());
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return res.toString();
    }
    
}
