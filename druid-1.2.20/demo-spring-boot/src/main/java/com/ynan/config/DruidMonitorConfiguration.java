package com.ynan.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidMonitorConfiguration {

    @Bean("druidStatViewServlet")
    public ServletRegistrationBean<StatViewServlet> getDruidStatViewServlet(){
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        registrationBean.addInitParameter(StatViewServlet.PARAM_NAME_ALLOW, "127.0.0.1"); // 白名单
        registrationBean.addInitParameter(StatViewServlet.PARAM_NAME_DENY, ""); // 黑名单
        registrationBean.addInitParameter(StatViewServlet.PARAM_NAME_USERNAME, "admin"); // 用户名
        registrationBean.addInitParameter(StatViewServlet.PARAM_NAME_PASSWORD, "123456"); // 密码
        registrationBean.addInitParameter(StatViewServlet.PARAM_NAME_RESET_ENABLE, "true"); // 允许重置
        return  registrationBean;
    }


}
