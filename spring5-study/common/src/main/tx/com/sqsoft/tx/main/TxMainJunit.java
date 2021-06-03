package com.sqsoft.tx.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sqsoft.tx.service.BookService;

public class TxMainJunit {
	
	@Autowired
	@Qualifier("bookServiceImpl")
	private BookService bookService;
	
	@Autowired
	@Qualifier("proxyFactoryBeanProxy")
	private BookService bookServiceProxy;
	

}
