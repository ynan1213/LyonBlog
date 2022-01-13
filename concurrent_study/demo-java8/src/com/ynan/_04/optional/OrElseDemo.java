package com.ynan._04.optional;

import java.util.Optional;

/**
 * @Author yuannan
 * @Date 2022/1/4 23:18
 */
public class OrElseDemo {

    public static void main(String[] args) {

        User user = null;

        User user1 = Optional.ofNullable(user).orElse(new User("orElse"));
        User user2 = Optional.ofNullable(user).orElseGet(() -> new User("orElseGet"));
        System.out.println(user1);
        System.out.println(user2);

        User use = new User("zhangsan");

        User user3 = Optional.ofNullable(use).orElse(new User("orElse"));
        User user4 = Optional.ofNullable(use).orElseGet(() -> new User("orElseGet"));
        System.out.println(user3);
        System.out.println(user4);
    }
}
