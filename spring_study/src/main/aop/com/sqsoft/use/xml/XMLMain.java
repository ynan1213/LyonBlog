package com.sqsoft.use.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class XMLMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/sqsoft/use/xml/application-xml.xml");
		SayService s = ac.getBean("serviceImpl",SayService.class);
		s.say("hello", "world");
//		s.say("hello");
		ac.close();
	}

}
