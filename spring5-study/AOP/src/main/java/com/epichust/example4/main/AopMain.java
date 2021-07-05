package com.epichust.example4.main;

import com.epichust.example4.config.RootConfig;
import com.epichust.example4.config.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epichust.example4.service.Dao;

public class AopMain
{
    public static void main(String[] args)
    {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(RootConfig.class);
		ac.refresh();

		//Dao dao = ac.getBean(Dao.class);
		//dao.select("hello world", 123, new User("张三", 23));

		Dao dao = ac.getBean("daoAnnotationImpl", Dao.class);
		dao.select("中国", 333, new User("张三", 23));
	}
}
