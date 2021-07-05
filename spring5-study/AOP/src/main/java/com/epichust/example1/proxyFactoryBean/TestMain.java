package com.epichust.example1.proxyFactoryBean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain
{
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/epichust/example1/proxyFactoryBean/proxyFactoryBean.xml");
        IBussinessService bussinessServiceImpl = ac.getBean("methodProxy", IBussinessService.class);
        bussinessServiceImpl.bussiness();
        ac.close();
    }
}
