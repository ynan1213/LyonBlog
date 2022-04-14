package com.ynan.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author yuannan
 * @Date 2022/4/14 22:15
 */
@Configuration
public class SentinelMvcConfig implements WebMvcConfigurer {

    // 参照 InterceptorConfig 类
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        SentinelWebMvcConfig config = new SentinelWebMvcConfig();
        config.setBlockExceptionHandler(new BlockExceptionHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
                String resourceName = e.getRule().getResource();
                //Depending on your situation, you can choose to process or throw
                if ("/hello".equals(resourceName)) {
                    //Do something ......
                    //Write string or json string;
                    response.getWriter().write("/Blocked by sentinel");
                } else {
                    //Handle in global exception handling
                    throw e;
                }
            }
        });

        //Custom configuration if necessary
        config.setHttpMethodSpecify(false);
        config.setWebContextUnify(true);
        config.setOriginParser(new RequestOriginParser() {
            @Override
            public String parseOrigin(HttpServletRequest request) {
                return request.getHeader("S-user");
            }
        });

        registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");

    }
}
