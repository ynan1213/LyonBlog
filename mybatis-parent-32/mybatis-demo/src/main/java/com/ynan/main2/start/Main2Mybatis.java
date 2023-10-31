package com.ynan.main2.start;

import com.ynan.main2.dao.UserDao;
import com.ynan.main2.entity.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @program: mybatis-parent
 * @author: yn
 * @create: 2021-07-25 12:02
 * @description:
 */
public class Main2Mybatis {

    SqlSession sqlSession;

    @Before
    public void before() throws IOException {
//        InputStream inputStream = Resources.getResourceAsStream("/Users/yuannan/GitProjects/Java-Study/mybatis-parent-32/mybatis-demo/src/main/resources/config.xml");
        File file = new File("/Users/yuannan/GitProjects/Java-Study/mybatis-parent-32/mybatis-demo/src/main/resources/config.xml");
        InputStream inputStream = new FileInputStream(file);
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void select() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.getById(6);
        System.out.println(user);

//        User user = new User();
//        user.setName("222");
//        user.setAge(22);
//        userDao.insert(user);

        sqlSession.commit();
//
//        sqlSession.rollback();
//
//        sqlSession.close();
    }

}
