package com.sqsoft.main;


import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sqsoft.lookup.A;
import com.sqsoft.lookup.B;

public class MainTest {

	public static void main(String[] args) {

		Properties properties = System.getProperties();
		properties.put("str", "sqsoft");
		
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/sqsoft/main/application.xml");
		A a = ac.getBean(A.class);
		B b = ac.getBean(B.class);
		
		System.out.println(a.getB() == b);
		
		ac.close();
	}
}
