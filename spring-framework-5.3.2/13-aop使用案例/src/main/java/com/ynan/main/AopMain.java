package com.ynan.main;

import com.ynan.config.RootConfig;
import com.ynan.entity.User;
import com.ynan.service.Dao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
