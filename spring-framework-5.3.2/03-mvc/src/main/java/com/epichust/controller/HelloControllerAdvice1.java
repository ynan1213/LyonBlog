package com.epichust.controller;

import com.epichust.expcetion.UserException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

//可以指定对指定包、指定类型生效
//@ControllerAdvice(basePackages = "com.xxxx")

@ControllerAdvice
public class HelloControllerAdvice1 implements ResponseBodyAdvice
{
	@ExceptionHandler(UserException.class)
	public String ex(UserException u)
	{
		return "error2";
	}

	@Override
	public boolean supports(MethodParameter returnType, Class converterType)
	{
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response)
	{
		System.out.println("ResponseBodyAdvice ---- beforeBodyWrite");
		return null;
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
