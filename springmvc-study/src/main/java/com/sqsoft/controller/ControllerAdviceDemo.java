package com.sqsoft.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class ControllerAdviceDemo {
	
	@ExceptionHandler(UserException.class)
	public String ex(UserException u) {
		System.out.println(u);
		System.out.println(u.getMessage());
		return "error2";
	}
}
