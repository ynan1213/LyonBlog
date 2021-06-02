package com.sqsoft.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler
	public String errorPage(Exception e,HttpServletRequest request) {
		String msg = e.getMessage();
		request.setAttribute("errorMsg", msg);
		return "error";
	}
	
}
