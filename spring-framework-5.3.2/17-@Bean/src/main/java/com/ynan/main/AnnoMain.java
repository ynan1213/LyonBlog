package com.ynan.main;

import com.ynan.config.RootConfig;
import com.ynan.config.Service;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author yuannan
 * @Date 2021/11/3 10:40
 */
public class AnnoMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		Service service = context.getBean(Service.class);
		System.out.println("hello");
	}
}
