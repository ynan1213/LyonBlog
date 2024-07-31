package com.ynan.service.impl;

import com.ynan.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
	@Override
	public String say(String xxx) {
		System.out.println(" ------------ HelloServiceImpl ------------- : " + xxx);
		return null;
	}
}
