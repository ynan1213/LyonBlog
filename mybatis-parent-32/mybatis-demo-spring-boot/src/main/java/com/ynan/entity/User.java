package com.ynan.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yuannan
 * @Date 2021/11/30 22:24
 */
@Data
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
