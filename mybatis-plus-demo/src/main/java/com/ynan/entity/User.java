package com.ynan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yuannan
 * @Date 2021/11/8 14:25
 */
//@TableName("t_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {

    // @TableId(type = IdType.AUTO)
    // 也可以使用全局配置
    private Integer id;

    private String Name;

    private Integer age;

    public User(String name, Integer age) {
        Name = name;
        this.age = age;
    }
}