package com.ynan;

import com.ynan.service.SimpleImport;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yuannan
 * @date 2022/11/16 20:30
 */
public class ImportMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImportRootConfig.class);

		SimpleImport bean = context.getBean(SimpleImport.class);
		System.out.println("--------- end ---------");

	}
}
