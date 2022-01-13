package com.ynan._03.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author yuannan
 * @Date 2021/12/26 20:59
 */
public class MapToIntDemo {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");

        long sum = list.stream().mapToInt(item -> item.length()).sum();
        long sum1 = list.stream().collect(Collectors.summingLong(String::length));
        System.out.println(sum);
        System.out.println(sum1);
    }
}
