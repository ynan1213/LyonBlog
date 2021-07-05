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
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

//可以指定对指定包、指定类型生效
//@ControllerAdvice(basePackages = "com.xxxx")

@ControllerAdvice
public class HelloControllerAdvice implements RequestBodyAdvice
{
	@ExceptionHandler(UserException.class)
	public String ex(UserException u)
	{
		return "error2";
	}

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType)
	{
		return true;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException
	{
		System.out.println("RequestBodyAdvice ---- beforeBodyRead");
		return null;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType)
	{
		System.out.println("RequestBodyAdvice ---- afterBodyRead");
		return null;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType)
	{
		System.out.println("RequestBodyAdvice ---- handleEmptyBody");
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
