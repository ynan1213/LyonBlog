package com.sqsoft.main;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.sqsoft.dao.MailDao;
import com.sqsoft.entity.Mail;

public class MyBatisProxy {
	
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void prepare() throws IOException {
		String resource = "config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Test
	public void testMyBatis() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			MailDao mailDao = sqlSession.getMapper(MailDao.class);
//			Mail mail = new Mail(12, "333@xx.com", "xxx");
//			RowBounds rb = new RowBounds();
//			long i = mailDao.insertMail(mail,rb,"aaa",1);
//			System.out.println(i);
			
			Mail mail = mailDao.selectMailById(2);
			System.out.println(mail);
			
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
	
}
