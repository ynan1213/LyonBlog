package com.epichust.servlet01;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 方法一：Spring Boot 注册
 */
@Configuration
public class HelloServletRegistrationBean
{
    @Bean
    public ServletRegistrationBean servletBean() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.addUrlMappings("/helloServlet01");
        registrationBean.setServlet(new HelloServlet());
        return registrationBean;
    }

}
