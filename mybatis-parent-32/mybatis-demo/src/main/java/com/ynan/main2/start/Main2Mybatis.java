package com.ynan.main2.start;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * @program: mybatis-parent
 * @author: yn
 * @create: 2021-07-25 12:02
 * @description:
 */
public class Main2Mybatis
{
    SqlSession sqlSession;

    @Before
    public void befor() throws IOException
    {
        InputStream inputStream = Resources.getResourceAsStream("/config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void select()
    {
        sqlSession.getMapper(String.class);
    }

    @Test
    public void insert()
    {
    }


}
