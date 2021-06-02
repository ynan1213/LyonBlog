package com.epichust.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandler
{
	
	@org.springframework.web.bind.annotation.ExceptionHandler
	public String errorPage(Exception e, HttpServletRequest request) {
		String msg = e.getMessage();
		request.setAttribute("errorMsg", msg);
		return "error";
	}
	
}
