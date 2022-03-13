package com.ynan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Author yuannan
 * @Date 2021/11/4 21:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long uid;
    private String name;
    private Integer status;
    private String statusName;
}
