package com.ynan.test;

import com.ynan.service.ProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author yuannan
 * @Date 2021/11/20 11:59
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test01 {

    @Autowired
    private ProducerService service;

    @Test
    public void test(){
        service.say("hello world");
    }
}
