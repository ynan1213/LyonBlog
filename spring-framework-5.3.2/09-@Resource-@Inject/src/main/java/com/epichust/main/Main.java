package com.epichust.main;

import com.epichust.config.RootConfig;
import com.epichust.controller.UserController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main
{
	public static void main(String[] args)
	{
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);

		UserController userController = context.getBean(UserController.class);

		userController.test();
	}
}
