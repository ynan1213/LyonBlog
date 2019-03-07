package com.sqsoft.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sqsoft.service.Dao;

public class AopMain {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/sqsoft/main/application-aop.xml");
		
		Dao dao = (Dao) ac.getBean(Dao.class);
		dao.select();
		System.out.println("-----------------------------------------------------------------------------------");
		dao.insert();

//		System.out.println("-----------------------------------------------------------------------------------");
//		
//		Extend ex = (Extend)dao;
//		ex.extendMethod();
		
		ac.close();
	}
}
