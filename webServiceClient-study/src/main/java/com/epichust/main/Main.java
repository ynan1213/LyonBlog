package com.epichust.main;

import com.epichust.main.service.IWeatherService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;

public class Main
{
    public static void main(String[] args)
    {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

        factoryBean.setAddress("http://localhost:8888/weatherService");

        factoryBean.setServiceClass(IWeatherService.class);

        IWeatherService service = (IWeatherService)factoryBean.create(IWeatherService.class);

        String s = service.sayHello("你好", 0506);
        System.out.println(s);
    }
}
