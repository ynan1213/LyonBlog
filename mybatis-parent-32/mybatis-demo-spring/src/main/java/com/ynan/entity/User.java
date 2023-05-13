package com.ynan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: mybatis-parent
 * @description:
 * @author: yn
 * @create: 2021-06-25 18:36
 */
@Data
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Integer gender;

    public User(String name, Integer gender) {
        this.name = name;
        this.gender = gender;
    }
}
