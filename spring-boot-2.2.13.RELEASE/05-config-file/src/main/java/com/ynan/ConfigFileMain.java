package com.ynan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Author yuannan
 * @Date 2022/3/30 11:47
 */
@SpringBootApplication
public class ConfigFileMain {

	/**
	 * --aaa=111
	 * --bbb
	 * --ccc=
	 * --ddd=""
	 * --eee
	 * =
	 * fff
	 * -ggg=ggggg
	 * -hhh
	 * xxx=yyy
	 * zzz
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ConfigFileMain.class, args);
		ConfigurableEnvironment environment = context.getEnvironment();
		String aaa = environment.getProperty("aaa");
		String bbb = environment.getProperty("bbb");
		String ccc = environment.getProperty("ccc");
		String ddd = environment.getProperty("ddd");
		String ggg = environment.getProperty("ggg");
		String nonOptionArgs = environment.getProperty("nonOptionArgs");
		System.out.println("hello");

	}
}
