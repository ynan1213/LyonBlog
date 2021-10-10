package com.ynan._01.comparable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Author yuannan
 * @Date 2021/9/25 9:55 上午
 */
public class Demo01 {

    public static void main(String[] args) {

        List<Student> list = Arrays.asList(
            new Student("zhangsan", 23),
            new Student("lisi", 24),
            new Student("dingyi", 21),
            new Student("wanger", 22),
            new Student("tianqi", 27),
            null
        );

        //        Collections.sort(list,(s1, s2) -> {
        //            return s1.getAge() - s2.getAge();
        //        });

//        list.sort((s1, s2) -> s1.getAge() - s2.getAge());
//        list.sort(Comparator.comparing(Student::getAge));

        //list.sort(Comparator.comparing(Student::getAge, (s1, s2) -> s2 - s1));

        //list.sort(Comparator.comparing(Student::getAge, Comparator.naturalOrder()));
        //list.sort(Comparator.comparing(Student::getAge, Comparator.reverseOrder()));
        //list.sort(Comparator.comparing(Student::getAge).reversed());

        list.sort(Comparator.nullsFirst(Comparator.comparing(Student::getName)));
        list.sort(Comparator.nullsLast(Comparator.comparing(Student::getName)));

        System.out.println(list);
    }
}
