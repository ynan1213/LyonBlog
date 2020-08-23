package com.sqsoft.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sqsoft.dao.AuthorDao;
import com.sqsoft.dao.UserDao;
import com.sqsoft.entity.AuthorDO;
import com.sqsoft.entity.User;

public class Test1
{

	private SqlSession sqlSession = null;
	
	@Before
	public void before() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("config.xml");
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		sqlSession = sessionFactory.openSession();
	}

	@After
	public void after() {
		if(sqlSession != null) {
			sqlSession.close();
		}
	}

	@Test
	public void test1() {
		UserDao userDao = sqlSession.getMapper(UserDao.class);
		int i = userDao.insert(new User(0, "赵六", 26, "武汉", "武大"));
		System.out.println(i);
		sqlSession.commit();
	}

	@Test
	public void test2() {
		UserDao userDao = sqlSession.getMapper(UserDao.class);
		User user = userDao.getById(2);
		User user1 = userDao.getById(2);
		System.out.println(user);
		sqlSession.commit();
	}

	@Test
	public void test3() {
		UserDao userDao = sqlSession.getMapper(UserDao.class);
		User user = new User(1, "xx", 20, "武汉", "华科");
		Set<User> set = userDao.selectByUser(user);
		for (User userDO : set) {
			System.out.println(userDO);
		}
	}
	
	@Test
	public void test4() {
		UserDao userDao = sqlSession.getMapper(UserDao.class);
		Set<User> set = userDao.selectOne("武汉");
		for (User user : set) {
			System.out.println(user);
		}
	}

	@Test
	public void test5() {
		AuthorDao authorDao = sqlSession.getMapper(AuthorDao.class);
		List<AuthorDO> list = authorDao.findOne(2, "", new RowBounds(), "");
		for (AuthorDO authorDO : list) {
			System.out.println(authorDO);
		}
		sqlSession.commit();
	}

}
