package com.sqsoft.main;


import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sqsoft.service.Service1;

public class MainTest {

	public static void main(String[] args) {

		Properties properties = System.getProperties();
		properties.put("str", "sqsoft");
		
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:application.xml");
		Service1 s1 = ac.getBean("service1",Service1.class);
		System.out.println(s1);
		ac.close();
	}
}
