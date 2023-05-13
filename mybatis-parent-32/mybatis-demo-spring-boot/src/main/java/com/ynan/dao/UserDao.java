package com.ynan.dao;

import com.ynan.entity.User;
import com.ynan.entity.UserCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Author yuannan
 * @Date 2021/11/30 22:25
 */
public interface UserDao {

    int insert(User user);

    User getById(@Param("id") int id, @Param("xxx") String xxx);

    List<User> get(@Param("condition") UserCondition userCondition, @Param("xxx") String xxx);

    User getById(String xxx, String yyy);
}
