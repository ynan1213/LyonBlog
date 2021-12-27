package com.ynan.controller;

import com.ynan.exception.MyException;
import com.ynan.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2021/12/27 11:33
 */
@RestController
public class HelloWordController {

	@Autowired
	private HelloService helloService;

	@RequestMapping("/hello")
	public String hello() throws MyException {
		helloService.hello();
		return "hello world !!!! ";
	}

	@RequestMapping("/world")
	public String world() throws MyException {
		helloService.world();
		return "hello world !!!! ";
	}
}
