package com.ynan.dao;

import com.ynan.entity.User;

/**
 * @Author yuannan
 * @Date 2021/11/30 22:25
 */
public interface UserDao {

    int insert(User user);

    User getById(int id);
}
