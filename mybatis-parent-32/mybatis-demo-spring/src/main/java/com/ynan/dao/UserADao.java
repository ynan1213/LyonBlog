package com.ynan.dao;

import com.ynan.entity.User;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * @program: mybatis-parent
 * @description:
 * @author: yn
 * @create: 2021-06-25 18:40
 */
public interface UserADao {

    int insert(User user);

    User getById(@Param("id") int id, @Param("xxx") String xxx);

//    @Flush
//    void flush();

//    @MapKey("name")
//    Map returnMap();
}
