package com.ynan._04.optional;

import java.util.Optional;
import java.util.function.Function;

/**
 * @Author yuannan
 * @Date 2022/1/4 23:06
 */
public class MapDemo {

    public static void main(String[] args) {

        Function<User, String> f1 = User::getName;
        Function<String, String> f2 = String::toUpperCase;

        Optional.of(new User("xxx"))
            .map(f1.andThen(f2))
            .ifPresent(System.out::println);


        Optional<String> xxx = Optional.ofNullable(new User("xxx"))
            .map(User::getName);

        Optional.ofNullable(new User("xxx")).flatMap(u -> Optional.ofNullable(u.getName()));


    }
}
