package com.sqsoft.service;

import com.sqsoft.dao.UserDao;
import com.sqsoft.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserDao userDao;

    public User test(int id)
    {
        return userDao.getById(id);
    }
}
