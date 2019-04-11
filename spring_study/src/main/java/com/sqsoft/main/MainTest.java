package com.sqsoft.main;


import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

	public static void main(String[] args) {

		Properties properties = System.getProperties();
		properties.put("str", "sqsoft");
		
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:application.xml");
		ac.close();
	}
}
