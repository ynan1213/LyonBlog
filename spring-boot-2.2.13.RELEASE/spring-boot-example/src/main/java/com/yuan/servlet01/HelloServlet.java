package com.epichust.servlet01;

import javax.servlet.*;
import java.io.IOException;

public class HelloServlet implements Servlet
{
	@Override
	public void init(ServletConfig servletConfig) throws ServletException
	{
		System.out.println("HelloServlet init ................");
	}

	@Override
	public ServletConfig getServletConfig()
	{
		return null;
	}

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException
	{
		System.out.println("HelloServlet service ................");
	}

	@Override
	public String getServletInfo()
	{
		return null;
	}

	@Override
	public void destroy()
	{

	}
}
