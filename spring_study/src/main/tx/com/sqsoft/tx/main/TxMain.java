package com.sqsoft.tx.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sqsoft.tx.entity.Book;
import com.sqsoft.tx.service.BookService;


public class TxMain {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		System.getProperties().setProperty("aaaa", "spring-application");
		
		/* 基于事物工厂的配置方式 */
//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:tx/spring-application-1.xml");
		
		/* 基于tx标签的配置方式 */
//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:tx/${aaaa}-2.xml") ;
		
		/* 基于@Aspect注解的方式 */
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:tx/spring-application-3.xml");

		BookService bs =(BookService) ac.getBean("bookServiceImpl");
		
		Book book = new Book("444",100);
		
		try
		{
			bs.insert(book);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		ac.close();
	}
	
}
