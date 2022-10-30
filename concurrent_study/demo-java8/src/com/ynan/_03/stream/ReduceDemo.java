package com.ynan._03.stream;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @Author yuannan
 * @Date 2021/12/26 21:56
 */
public class ReduceDemo {

    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});

        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        stream.reduce(Integer::max).ifPresent(System.out::println);
    }
}
