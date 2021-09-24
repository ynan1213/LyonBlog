package com.ynan.dao;

import com.ynan.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: mybatis-parent
 * @description:
 * @author: yn
 * @create: 2021-06-25 18:40
 */
public interface UserDao
{
    int insert(User user);

    User getById(int id);

    List<User> getByName(@Param("xx") String name);
}
