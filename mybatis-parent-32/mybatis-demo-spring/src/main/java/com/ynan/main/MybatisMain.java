package com.ynan.main;

import com.ynan.config.RootAnnoConfig;
import com.ynan.config.RootConfig;
import com.ynan.dao.UserDao;
import com.ynan.entity.User;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @program: mybatis-parent
 * @description:
 * @author: yn
 * @create: 2021-06-25 18:41
 */
public class MybatisMain {

    @Test
    public void test() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(RootAnnoConfig.class);
        UserDao userDao = ac.getBean(UserDao.class);

        User user = userDao.getById(1);
        System.out.println(user);
    }

    @Test
    public void test1() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(RootConfig.class);
        TransactionTemplate transactionTemplate = ac.getBean(TransactionTemplate.class);
        UserDao userDao = ac.getBean(UserDao.class);

        transactionTemplate.execute(status -> {
            Integer integer = new Integer(1);
            User user1 = userDao.getById(integer);
            User user2 = userDao.getById(integer);
            System.out.println(user1);
            System.out.println(user2);
            return user1;
        });
    }

    @Test
    public void test2() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(RootConfig.class);
        UserDao userDao = ac.getBean(UserDao.class);
        for (int i = 0; i < 10; i++) {
            userDao.insert(new User(i + "", i));
        }
    }

    @Test
    public void test3() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(RootConfig.class);
        SqlSessionFactory sqlSessionFactory = ac.getBean(SqlSessionFactory.class);
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        for (int i = 0; i < 10; i++) {
            userDao.insert(new User(i + "", i));
        }
        //        sqlSession.flushStatements();
        //        userDao.flush();
    }

    @Test
    public void test4map() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(RootConfig.class);
        UserDao userDao = ac.getBean(UserDao.class);
        Map map = userDao.returnMap();
        System.out.println(map);
    }

}
