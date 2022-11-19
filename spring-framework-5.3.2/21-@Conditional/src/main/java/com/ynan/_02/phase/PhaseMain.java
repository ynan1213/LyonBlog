package com.ynan._02.phase;

import java.util.Map;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yuannan
 * @date 2022/11/16 10:05
 */
public class PhaseMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(PhaseRootConfig.class);
		context.refresh();
		Map<String, Service> beans = context.getBeansOfType(Service.class);
		System.out.println("----- end -----");
	}
}
