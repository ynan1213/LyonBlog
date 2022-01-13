package com.ynan._05.collect;

import com.ynan._01.comparable.Student;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author yuannan
 * @Date 2022/1/5 21:41
 */
public class CollectingAndThenDemo {

    public static void main(String[] args) {
        Stream<Student> stream = Stream.of(
            new Student("111", 11),
            new Student("222", 22),
            new Student("111", 33),
            new Student("333", 33));

        List<Student> collect = stream.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

    }
}
