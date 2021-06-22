package com.epichust.main;

import com.epichust.entity.Book;
import com.epichust.config.AppConfig;
import com.epichust.service.BookService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TxMain
{

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		System.getProperties().setProperty("aaaa", "spring-application");

		/* 基于事物工厂的配置方式 */
		//ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-application-1.xml");

		/* 基于tx标签的配置方式 */
		//ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:${aaaa}-2.xml") ;

		/* 基于@Aspect注解的方式 */
		//ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-application-3.xml");

		/* 基于@Aspect注解的方式 */
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		BookService bs = (BookService) ac.getBean("bookServiceImpl");

		Book book = new Book("4444", 4444);

		try
		{
			int result = bs.insert(book);
			System.out.println(result);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		ac.close();
	}
}
