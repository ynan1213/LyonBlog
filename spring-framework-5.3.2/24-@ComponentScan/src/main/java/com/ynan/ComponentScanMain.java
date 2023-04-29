package com.ynan;

import com.ynan.config.ComponentScanConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yuannan
 */
public class ComponentScanMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanConfig.class);

	}

}
