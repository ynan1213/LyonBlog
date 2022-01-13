package com.ynan._03.stream;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author yuannan
 * @Date 2021/12/6 23:08
 */
public class ToMapDemo {

    public static void main(String[] args) {
        Stream<Student> stream = Stream.of(
            new Student("111", 11),
            new Student("222", 22),
            new Student("111", 33),
            new Student("333", 33));

//        Map<String, Student> map = stream.collect(Collectors.toMap(Student::getName, Function.identity()));
//        Map<String, Integer> map = stream.collect(Collectors.toMap(Student::getName, Student::getAge));
        Map<Integer, Student> map = stream.collect(Collectors.toMap(Student::getAge, student -> student, (s1, s2) -> s2));
        System.out.println(map);

    }


    static class Student {

        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
