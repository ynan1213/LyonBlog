package com.ynan._03.stream;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @Author yuannan
 * @Date 2021/9/25 9:57 下午
 */
public class ToArrayDemo {

    public static void main(String[] args) {
        IntStream intStream = Arrays.stream(new int[]{1, 3, 5, 7, 8});
        int[] intArray = intStream.toArray();

        LongStream longStream = Arrays.stream(new long[]{1, 3, 5, 7, 9});
        long[] longArray = longStream.toArray();

        DoubleStream doubleStream = Arrays.stream(new double[]{1, 3, 5, 7, 9});
        double[] doubleArray = doubleStream.toArray();

        Stream<String> stringStream = Arrays.stream(new String[]{"hello", "world", "nihao"});
        Object[] objectArray = stringStream.toArray();

        String[] stringArray = stringStream.toArray(length -> new String[length]);
        stringStream.toArray(String[]::new);


    }

}
