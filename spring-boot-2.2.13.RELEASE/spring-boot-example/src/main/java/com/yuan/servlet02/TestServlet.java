package com.epichust.servlet02;

import com.epichust.servlet01.HelloServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 测试什么都不加，能不能被springboot添加到servlet容器中
 *
 * 	答案：并不会
 */
//@Component
public class TestServlet implements Servlet
{

	public TestServlet()
	{
		System.out.println("TestServlet 被添加到Spring容器 =======================================");
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException
	{
		System.out.println("TestServlet init =======================================");
	}

	@Override
	public ServletConfig getServletConfig()
	{
		return null;
	}

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException
	{

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

	// 只有这样才行
	/*@Bean
	public ServletRegistrationBean getServletBean() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean();
		registrationBean.addUrlMappings("/helloServlet02");
		registrationBean.setServlet(new TestServlet());
		return registrationBean;
	}*/

}
