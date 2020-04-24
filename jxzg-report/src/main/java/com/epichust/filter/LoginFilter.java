package com.epichust.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter
{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (request.getServletPath().endsWith(".ico") || request.getServletPath().endsWith(".css") 
				|| request.getServletPath().endsWith(".js") || request.getServletPath().endsWith(".png")
				|| request.getServletPath().endsWith(".jpg"))
		{
			chain.doFilter(request, response);
		} else if ("/login".equals(request.getServletPath()))
		{
			if(request.getSession().getAttribute("username") != null)
			{
				response.sendRedirect("/index");
			}else
			{
				chain.doFilter(request, response);
			}
		} else if("/index".equals(request.getServletPath()))
		{
			if(request.getSession().getAttribute("username") != null)
			{
				chain.doFilter(request, response);
			}else
			{
				response.sendRedirect("/login");
			}
		}else
		{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy()
	{

	}
}
