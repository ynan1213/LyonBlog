package com.ynan;

import com.ynan.config.DependConfig;
import com.ynan.service.OneServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yuannan
 */
public class DependMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DependConfig.class);

		OneServiceImpl bean = context.getBean(OneServiceImpl.class);
		System.out.println("--------- end ---------");

	}
}
