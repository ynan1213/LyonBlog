package com.ynan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author yuannan
 * @Date 2021/12/27 11:30
 */
@SpringBootApplication
public class MvcExceptionMain {

	public static void main(String[] args) {
		SpringApplication.run(MvcExceptionMain.class, args);
	}
}
