package com.yuan.example4.main;


import java.util.Properties;

import com.epichust.example4.service.FatherService;
import com.epichust.example4.service.TestBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sqsoft.lookup.A;
import com.sqsoft.lookup.B;

public class MainTest {

	public static void main(String[] args) {

		Properties properties = System.getProperties();
		properties.put("str", "sqsoft");
		
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/epichust/example4/main/application.xml");
//		A a = ac.getBean(A.class);
//		B b = ac.getBean(B.class);
//
//		System.out.println(a.getB() == b);

		TestBean bean = ac.getBean(TestBean.class);
		String[] beanNamesForType = ac.getBeanNamesForType(TestBean.class);
		bean.print();

		ac.close();
	}
}
