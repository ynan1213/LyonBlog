package com.epichust.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class MyServlet implements Servlet
{
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        System.out.println("MyServlet init ==============================================================");
    }

    @Override
    public ServletConfig getServletConfig()
    {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
    {
        System.out.println("MyServlet service ==============================================================");
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
