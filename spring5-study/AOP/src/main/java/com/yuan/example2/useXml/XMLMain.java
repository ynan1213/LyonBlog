package com.epichust.example2.useXml;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class XMLMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/epichust/example2/useXml/application-xml.xml");
		SayService s = ac.getBean("serviceImpl",SayService.class);
		s.say("hello", "world");
//		s.say("hello");
		ac.close();
	}

}
