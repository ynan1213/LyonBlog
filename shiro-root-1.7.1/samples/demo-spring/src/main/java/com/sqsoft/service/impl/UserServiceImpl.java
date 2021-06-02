package com.sqsoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqsoft.dao.UserDao;
import com.sqsoft.entity.User;
import com.sqsoft.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public User selectByUid(int uid) {
		return userDao.selectByUid(uid);
	}

	@Override
	public User selectByName(String username) {
		return userDao.selectByName(username);
	}

}
