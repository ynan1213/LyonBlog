package com.ynan._03.stream;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author yuannan
 * @Date 2021/12/7 09:48
 */
public class GroupByDemo {

    public static void main(String[] args) {
        Stream<Student> stream = Stream.of(
            new Student("111", 11),
            new Student("222", 22),
            new Student("111", 33),
            new Student("333", 33));
        Map<String, List<Student>> map = stream.collect(Collectors.groupingBy(Student::getName));
//        stream.collect(Collectors.groupingBy())
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

        @Override
        public String toString() {
            return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
        }
    }
}
