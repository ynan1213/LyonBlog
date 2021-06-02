package com.sqsoft.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 只要把filter交给spring 容器管理
 *  1.就会被springboot配置到servelt容器中
 *  2.也会被shiro扫描到，但是有个坑，不一定被扫描到，具体看笔记
 */
@Component
public class MyFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        System.out.println("MyFilter init ==============================================================");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        System.out.println("MyFilter doFilter ==============================================================");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {

    }
}
