package com.ynan.main;

import com.ynan.config.RootConfig;
import com.ynan.entity.User;
import com.ynan.service.Dao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;

public class AopMain
{
	public static void main(String[] args)
	{
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(RootConfig.class);
		ac.refresh();

		//Dao dao = ac.getBean(Dao.class);
		//dao.select("hello world", 123, new User("张三", 23));

		Dao dao = ac.getBean(Dao.class);
		dao.select("中国", 333, new User("张三", 23));

		// System.out.println("subject 的Class类是：" + dao.getClass().toString());
		//
		// System.out.print("subject中的方法有：");
		// Method[] method = dao.getClass().getDeclaredMethods();
		// for (Method m : method)
		// {
		// 	System.out.print(m.getName() + ", ");
		// }
		//
		// System.out.println("\n" + "subject的父类是：" + dao.getClass().getSuperclass());
		//
		// System.out.print("subject中的接口有：");
		// Class<?>[] interfaces = dao.getClass().getInterfaces();
		// for (Class i : interfaces)
		// {
		// 	System.out.print(i.getName() + ", ");
		// }


	}
}
