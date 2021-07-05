package com.epichust.example4.service;

import com.epichust.example4.config.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DaoImpl implements Dao
{
    @Override
    public void select(String sssss, int iiii, User user)
    {
        System.out.println("DaoImpl select-----------------------");
    }

    @Override
    public void insert()
    {
        System.out.println("DaoImpl insert-----------------------");
    }

}
