package com.sqsoft.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.sqsoft.entity.UserDO;

public class MyBatisTest {

	private SqlSession sqlSession = null;
	
	@Before
	public void before() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("config.xml");
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		sqlSession = sessionFactory.openSession();
	}
	
	@Test
	public void test1() {
		UserDao userDao = sqlSession.getMapper(UserDao.class);
		Set<UserDO> set = userDao.selectByCondition("武汉", 20, new RowBounds(), "华科");
		for (UserDO userDO : set) {
			System.out.println(userDO);
		}
	}
	@Test
	public void test2user() {
		UserDao userDao = sqlSession.getMapper(UserDao.class);
		UserDO user = new UserDO(1, "xx", 20, "武汉", "华科");
		Set<UserDO> set = userDao.selectByUser(user);
		for (UserDO userDO : set) {
			System.out.println(userDO);
		}
	}
	
	@Test
	public void test1one() {
		UserDao userDao = sqlSession.getMapper(UserDao.class);
		Set<UserDO> set = userDao.selectOne("武汉");
		for (UserDO userDO : set) {
			System.out.println(userDO);
		}
	}
	
	@Test
	public void test2() {
		UserDao userDao = sqlSession.getMapper(UserDao.class);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("uname", "ooo");
		map.put("uage", 33);
		map.put("addr", "ooo");
		map.put("school", "ooo");
		
		int i = userDao.insert1(map);
		System.out.println("uid="+map.get("uid"));
		System.out.println(i);
		
		sqlSession.commit();
	}
	
	@Test
	public void test3() {
		AuthorDao authorDao = sqlSession.getMapper(AuthorDao.class);
		List<AuthorDO> list = authorDao.findOne(2, "", new RowBounds(), "");
		for (AuthorDO authorDO : list) {
			System.out.println(authorDO);
		}
		sqlSession.commit();
	}
	
	@After
	public void after() {
		if(sqlSession != null) {
			sqlSession.close();
		}
	}
	

}
