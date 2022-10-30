package com.ynan._04.optional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author yuannan
 * @Date 2022/2/11 16:23
 */
public class SimpleDemo {

    public static void main(String[] args) {

        Map<String, User> map = new HashMap<>();
        map.put("11", new User("21"));

        Boolean result = Optional.ofNullable(map.get("11"))
            .map(user -> user.getName().equals("11"))
            .orElse(false);
        System.out.println(result);


    }
}
