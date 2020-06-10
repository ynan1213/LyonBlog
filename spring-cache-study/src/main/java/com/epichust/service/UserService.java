package com.epichust.service;

import com.epichust.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService
{

    @Cacheable(value = "user", keyGenerator = "springCacheKeyGenerator")
    public String hello(Integer msg, User user)
    {
        return "Hello : " + msg + " " + user.getUsername();
    }


    @Cacheable(value = "user", keyGenerator = "springCacheKeyGenerator")
    public User getInfo(Integer msg, User user)
    {
        return new User("李四", 24);
    }

}