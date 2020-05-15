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

}