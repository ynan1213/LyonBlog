package com.ynan.config;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuannan
 * @Date 2022/4/14 21:28
 */
@Configuration(proxyBeanMethods = false)
public class SentinelConfig {

    @Bean
    public FilterRegistrationBean sentinelFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sentinelFilter");
        registration.setOrder(1);

        Map<String, String> initParam = new HashMap<>();
        // 默认情况下，CommonFilter 是以url为resourceName
        // 将这个参数指定为true之后，会以Http请求 Method:url 作为resourceName
        // 比如 GET:/hello、POST:/hello，如果没开启，这两个请求是一个resourceName，如果开启了，是两个resourceName
        initParam.put(CommonFilter.HTTP_METHOD_SPECIFY, "true");

        registration.setInitParameters(initParam);
        return registration;
    }

}
