package com.epichust.main;

import com.epichust.main.service.IWeatherService;
import com.epichust.main.service.WeatherServiceImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;

import javax.xml.ws.Endpoint;

public class Main
{
    public static void main(String[] args)
    {
        JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
        //发布的地址
        factoryBean.setAddress("http://localhost:8888/weatherService");
        //服务接口类
        factoryBean.setServiceClass(IWeatherService.class);
        //服务实现类
        factoryBean.setServiceBean(new WeatherServiceImpl());

        factoryBean.create();
        System.out.println("服务发布成功！");
    }
}
