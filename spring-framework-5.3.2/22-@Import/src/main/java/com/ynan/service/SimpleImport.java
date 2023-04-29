package com.ynan.service;

import org.springframework.context.annotation.Bean;

/**
 * @author yuannan
 * @date 2022/11/16 20:30
 */
public class SimpleImport {

	@Bean
	private String xxx() {
		return new String("xxx");
	}

}
