package com.ynan._03.autoproxy;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AMain
{
	public static void main(String[] args) throws Exception
	{
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("application-aop.xml");

		Service s = (Service) ac.getBean("serviceImpl");
		String str = s.select("coso");
		System.out.println(str);

		ac.close();
	}
}
