package com.ynan._03.stream;

import java.util.stream.IntStream;

/**
 * @Author yuannan
 * @Date 2021/9/25 9:40 下午
 */
public class IntStreamDemo {

    public static void main(String[] args) {
        IntStream.range(3, 8).forEach(System.out::println);
        System.out.println("----------------------------------");
        IntStream.rangeClosed(3, 8).forEach(System.out::println);
    }

}
