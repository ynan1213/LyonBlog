package com.ynan._03.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author yuannan
 * @Date 2021/12/26 21:56
 */
public class ReduceDemo {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 4, 5, 6, 7);

        Optional<Integer> reduce = list.stream().reduce((x, y) -> {
            System.out.println(x);
            return x + y;
        });
        System.out.println(reduce.get());
        System.out.println("-----------");

        Integer integer = list.stream().reduce(2, (x, y) -> {
            System.out.println(x);
            return x + y;
        });
        System.out.println(integer);
    }
}
