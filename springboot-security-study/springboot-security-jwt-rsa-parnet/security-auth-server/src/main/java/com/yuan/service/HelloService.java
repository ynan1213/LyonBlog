package com.epichust.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloService implements UserDetailsService
{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("XXXX");
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(simpleGrantedAuthority);

        return new User("zhangsan", "$2a$10$4OZfwiQ9QtM0uQtyGd6dT.0PuYVoO/cTRGUgPLk2ptlFC1BQLGZPO", true, true, true, true, list);
    }


    public static void main(String[] args)
    {
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);
    }
}
