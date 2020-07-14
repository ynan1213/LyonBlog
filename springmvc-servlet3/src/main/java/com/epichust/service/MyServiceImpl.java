package com.epichust.service;

import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService
{
    @Override
    public String print(String msg)
    {
        return "Hello:" + msg;
    }
}
