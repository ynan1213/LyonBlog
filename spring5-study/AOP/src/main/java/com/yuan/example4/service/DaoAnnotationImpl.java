package com.epichust.example4.service;

import com.epichust.example4.config.InterfaceLog;
import com.epichust.example4.config.User;
import org.springframework.stereotype.Service;

@Service
public class DaoAnnotationImpl implements Dao
{
    @Override
    @InterfaceLog(name = "xxxxx", code = "1234556")
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
