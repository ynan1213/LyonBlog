package com.epichust.controller;

import com.epichust.expcetion.UserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//可以指定对指定包、指定类型生效
//@ControllerAdvice(basePackages = "com.xxxx")

@ControllerAdvice
public class HelloControllerAdvice
{
	@ExceptionHandler(UserException.class)
	public String ex(UserException u)
	{
		System.out.println(u);
		System.out.println(u.getMessage());
		return "error2";
	}

//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.setDisallowedFields("name");
//		System.out.println("initBinder.....ControllerAdvice.............................");
//	}
//
//	@ModelAttribute
//	public void modelAttribute() {
//		System.out.println("modelAttribute.....ControllerAdvice.............................");
//	}

}
