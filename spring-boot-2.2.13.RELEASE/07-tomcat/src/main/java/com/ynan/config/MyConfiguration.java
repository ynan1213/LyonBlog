package com.ynan.config;

import com.ynan.filter.HelloFilter;
import com.ynan.listener.HelloListener;
import com.ynan.servlet.HelloServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuannan
 */
@Configuration
public class MyConfiguration {

	@Bean
	public ServletRegistrationBean<HelloServlet> helloServlet() {
		ServletRegistrationBean<HelloServlet> registrationBean = new ServletRegistrationBean();
		registrationBean.addUrlMappings("/helloServlet");
		registrationBean.setServlet(new HelloServlet());
		return registrationBean;
	}

//	@Bean
//	public FilterRegistrationBean<HelloFilter> helloFilter() {
//		FilterRegistrationBean<HelloFilter> registrationBean = new FilterRegistrationBean<>();
//		registrationBean.setFilter(new HelloFilter());
//		return registrationBean;
//	}

//	@Bean
//	public ServletListenerRegistrationBean<HelloListener> helloListener() {
//		ServletListenerRegistrationBean<HelloListener> registrationBean = new ServletListenerRegistrationBean<>();
//		registrationBean.setListener(new HelloListener());
//		return registrationBean;
//	}
}
