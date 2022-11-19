package com.ynan.main;

import com.ynan.config.RootConfig1;
import com.ynan.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: spring
 * @description:
 * @author: yn
 * @create: 2021-06-22 17:23
 */
public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig1.class);
		UserService userService = context.getBean(UserService.class);
		userService.print("1111");

	}
}
