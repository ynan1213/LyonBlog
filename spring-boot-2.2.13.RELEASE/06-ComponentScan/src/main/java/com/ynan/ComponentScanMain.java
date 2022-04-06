package com.ynan;

import com.ynan.config01.Demo1;
import com.ynan.config02.Demo2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @Author yuannan
 * @Date 2022/4/1 17:01
 */
@SpringBootApplication
public class ComponentScanMain {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ComponentScanMain.class, args);
		context.getBean(Demo1.class);
		context.getBean(Demo2.class);
	}

	@Bean
	public TypeExcludeFilter get(){
		return new MyTypeExcludeFilter();
	}


}
