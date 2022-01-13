package com.ynan._03.stream;

import java.util.stream.Stream;

/**
 * @Author yuannan
 * @Date 2021/12/26 21:32
 */
public class FlatMapDemo {

    public static void main(String[] args) {
        // 将字符串数组转换成字符串，并去重
        String[] words = {"hello", "world"};

        Stream<String[]> stream = Stream.of(words).map(s -> s.split(""));

        Stream<String> stringStream = stream.flatMap(Stream::of);

        stringStream.distinct().forEach(System.out::println);

    }
}
