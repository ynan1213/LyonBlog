package com.ynan.main;

import com.ynan.config.RootConfig3;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author yuannan
 * @Date 2021/11/3 10:40
 */
public class AnnoMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig3.class);
		System.out.println("hello");
	}
}
