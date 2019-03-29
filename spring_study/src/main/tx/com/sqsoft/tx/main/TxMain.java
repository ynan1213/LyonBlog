package com.sqsoft.tx.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sqsoft.tx.entity.Book;
import com.sqsoft.tx.service.BookService;

public class TxMain {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:tx/spring-application.xml");
		BookService bs =(BookService) ac.getBean("bookServiceImpl");
		BookService bsp =(BookService) ac.getBean("proxyFactoryBeanProxy");
		
		Book book = new Book("python",50);
		System.out.println(bs.insert(book));
		ac.close();
	}
	
}
