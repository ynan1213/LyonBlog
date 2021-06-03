package com.epichust.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epichust.service.AService;

public class Main
{
	public static void main(String[] args)
	{
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:xml/spring-application.xml");
		AService a = (AService)ac.getBean("aService");
		a.print();
		ac.close();
	}
}
