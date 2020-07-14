package com.epichust.bean;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloServletRegistrationBean
{
    @Bean
    public ServletRegistrationBean servletBean() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.addUrlMappings("/register");
        registrationBean.setServlet(new HelloServlet());
        return registrationBean;
    }

}
