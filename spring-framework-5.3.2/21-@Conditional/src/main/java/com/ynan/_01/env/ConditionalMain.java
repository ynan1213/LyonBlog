package com.ynan._01.env;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yuannan
 * @date 2022/11/16 09:29
 */
public class ConditionalMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				RootConfig.class);
		System.out.println("===========");
	}

}
