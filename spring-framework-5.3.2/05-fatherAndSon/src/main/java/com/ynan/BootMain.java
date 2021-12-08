package com.ynan;

import com.ynan.fatherAndSon.Father;
import com.ynan.fatherAndSon.Son;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author yuannan
 * @Date 2021/12/8 22:05
 */
@SpringBootApplication
public class BootMain {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BootMain.class, args);
		Father father = context.getBean(Father.class);
		Son son = context.getBean(Son.class);
		System.out.println("1111111");
	}
}
