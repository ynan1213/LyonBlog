package com.ynan.main;

import com.ynan.config.RootConfig;
import com.ynan.dao.UserDao;
import com.ynan.entity.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * @program: mybatis-parent
 * @description:
 * @author: yn
 * @create: 2021-06-25 18:41
 */
public class MybatisMain
{
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(RootConfig.class);
        UserDao userDao = ac.getBean(UserDao.class);

        // User user = new User("11", 11, "11", "11");
        // userDao.insert(user);
        // System.out.println(user.getUid());

        List<User> xx = userDao.getByName("xx");
        System.out.println(xx);
    }
}
