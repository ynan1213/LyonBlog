package com.ynan.service.impl;

import com.ynan.entity.User;
import com.ynan.service.Dao;
import org.springframework.stereotype.Service;

@Service
public class DaoImpl implements Dao
{
	@Override
	public User select(String sssss, int iiii, User user)
	{
		System.out.println("DaoImpl select-----------------------");
		// if(1 == 1)
		// 	throw new RuntimeException("出错");
		return new User();
	}

	@Override
	public void insert()
	{
		System.out.println("DaoImpl insert-----------------------");
	}

}
