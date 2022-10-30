package com.ynan._03.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author yuannan
 * @Date 2021/9/25 9:40 下午
 */
public class IntStreamDemo {

    public static void main(String[] args) {
        IntStream.range(3, 8).forEach(System.out::println);
        System.out.println("----------------------------------");
        IntStream.rangeClosed(3, 8).forEach(System.out::println);
        System.out.println("----------------------------------");

        Stream<Integer> stream = Arrays.asList(1, 2, 4, 5, 6, 7).stream();
        Optional<Integer> reduce = stream.filter(i -> i > 3).reduce(Integer::sum);
        System.out.println(reduce.get());
        // 推荐使用下面的方式，为什么呢？ 因为 IntStream 内部使用的是int，占用内存少
        stream = Arrays.asList(1, 2, 4, 5, 6, 7).stream();
        int sum = stream.mapToInt(i -> i.intValue()).filter(i -> i > 3).sum();
        System.out.println(sum);


        // 找出a在下面字符串中所有的索引位置
        String s = "aadbadfiadosfdlsfjsdiouafdoiasfd";
        List<Integer> integerList = IntStream.range(0, s.length())
            .filter(index -> s.charAt(index) == 'a')
            .boxed()
            .collect(Collectors.toList());
        System.out.println(integerList);

    }

}
