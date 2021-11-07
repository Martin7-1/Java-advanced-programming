package com.nju.edu.generate.random;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class ImperativeRandoms {

    public static void main(String[] args) {
        Random rand = new Random(47);
        // 使用命令式编程，我们并不能直接的按照我们想做的步骤来实现程序
        SortedSet<Integer> rints = new TreeSet<>();

        while (rints.size() < 7) {
            // 生成一个0-20的整数
            int r = rand.nextInt(20);
            if (r < 5) {
                continue;
            }
            rints.add(r);
        }

        System.out.println(rints);
    }
    
}
