package com.sqsoft.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class ControllerAdviceDemo {
	
	@ExceptionHandler(UserException.class)
	public String ex() {
		return "error2";
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String ex1() {
		return "error2";
	}
	
	@ExceptionHandler(Exception.class)
	public String ex2() {
		return "error2";
	}
	
}
