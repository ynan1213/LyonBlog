package com.ynan._04.optional;

import java.util.Optional;

/**
 * @Author yuannan
 * @Date 2022/1/4 23:06
 */
public class MapDemo {

    public static void main(String[] args) {
        User user = new User("lisi");

        String s1 = Optional.ofNullable(user).map(User::getName).orElse("unKnown");
        System.out.println(s1);




    }
}
