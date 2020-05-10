package com.epichust.main.service;

import javax.jws.WebService;

@WebService
public interface IWeatherService
{
    public String sayHello(String name, int i);

    public String sayBye(boolean bye);
}
