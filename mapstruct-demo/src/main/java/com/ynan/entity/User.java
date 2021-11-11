package com.ynan.entity;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author yuannan
 * @Date 2021/11/6 20:30
 */
@Data
@AllArgsConstructor
public class User {

    private String name;

    private int age;

    private Date bir;

    private double price;

    private Dog dog;

    private List<Long> money;
}
