package com.main;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;

@RestController
@Scope("request")
public class HelloController {

	public HelloController () {
		System.out.println("HelloController init ...................... ");
	}

	@RequestMapping("/hello")
	public String hello() {
		return "hello world!!!";
	}

	@PreDestroy
	public void destoryxxx() {
		System.out.println("HelloController destory .....................");
	}

}
