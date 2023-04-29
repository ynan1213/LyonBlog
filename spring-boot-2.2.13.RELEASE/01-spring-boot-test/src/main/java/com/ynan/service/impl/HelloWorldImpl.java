package com.ynan.service.impl;

import com.ynan.service.HelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @Author yuannan
 * @Date 2021/11/17 20:16
 */
@Service
//@ConditionalOnBean
public class HelloWorldImpl implements HelloService {

	@Override
	public String say() {
		return "Hello World !!!";
	}
}
