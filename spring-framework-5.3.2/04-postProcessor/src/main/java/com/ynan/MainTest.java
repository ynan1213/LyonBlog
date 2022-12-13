package com.ynan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yuannan
 */
public class MainTest {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(String.class, MyBeanPostProcessor.class);
		context.refresh();
		context.getBean(UserService1.class);
		System.out.println("--------- end ----------");
	}

}
