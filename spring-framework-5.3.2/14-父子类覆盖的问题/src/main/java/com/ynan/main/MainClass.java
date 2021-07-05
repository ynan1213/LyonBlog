package com.ynan.main;

import com.ynan.config.RootConfig;
import com.ynan.entity.Father;
import com.ynan.entity.Son;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: spring
 * @description:
 * @author: yn
 * @create: 2021-07-01 23:05
 */
public class MainClass
{
	public static void main(String[] args)
	{
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(RootConfig.class);
		ac.refresh();

		Son son = ac.getBean(Son.class);
		son.print();
	}
}
