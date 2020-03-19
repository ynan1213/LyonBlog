package com.sqsoft.tx.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sqsoft.tx.service.BookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:tx/spring-application.xml")
public class TxMainJunit {
	
	@Autowired
	@Qualifier("bookServiceImpl")
	private BookService bookService;
	
	@Autowired
	@Qualifier("proxyFactoryBeanProxy")
	private BookService bookServiceProxy;
	
	@Test
	public void insert() {
	}
	
}
