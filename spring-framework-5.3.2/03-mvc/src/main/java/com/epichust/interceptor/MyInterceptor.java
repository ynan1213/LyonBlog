package com.epichust.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		System.out.println(request.getRequestURI());
		if("/favicon.ico".equals(request.getRequestURI()))
		{
			return false;
		}
		return true;
	}
}
