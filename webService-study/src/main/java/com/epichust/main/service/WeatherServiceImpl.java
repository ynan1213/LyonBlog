package com.epichust.main.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

public class WeatherServiceImpl implements IWeatherService
{
    public String sayHello(String name, int i)
    {
        return "name: "+ name + " i: " + i;
    }

    public String sayBye(boolean bye)
    {
        return null;
    }
}
