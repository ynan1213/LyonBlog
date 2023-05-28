package com.ynan.main;

import com.ynan.config.RootConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author yuannan
 * @Date 2021/11/13 10:24
 */
public class StartMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(RootConfig.class);
		ac.start();
//		ac.close();
	}
}
