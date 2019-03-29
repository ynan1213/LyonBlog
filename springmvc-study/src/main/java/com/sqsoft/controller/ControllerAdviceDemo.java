package com.sqsoft.controller;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.sqsoft.entity.User;

@SuppressWarnings("rawtypes")
//@ControllerAdvice()
public class ControllerAdviceDemo implements ResponseBodyAdvice{
	
	@ModelAttribute  
    public User newUser(@ModelAttribute("attr1")String attr1,@ModelAttribute("attr2")String attr2) {  
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");  
        return new User();  
    }
  
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");  
    }  
  
    @ExceptionHandler(RuntimeException.class)  
    public String processUnauthenticatedException() {  
        System.out.println("===========应用到所有@RequestMapping注解的方法，在其抛出UnauthenticatedException异常时执行");  
        return "viewName"; //返回一个逻辑视图名  
    }

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		System.out.println("ResponseBodyAdvice supports method");
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		// TODO Auto-generated method stub
		return "";
	}
	
}
