package com.ynan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author yuannan
 * @Date 2022/3/30 11:47
 */
@SpringBootApplication
public class ConfigFileMain {

	public static void main(String[] args) {
		SpringApplication.run(ConfigFileMain.class, new String[]{"--debug"});
	}
}
