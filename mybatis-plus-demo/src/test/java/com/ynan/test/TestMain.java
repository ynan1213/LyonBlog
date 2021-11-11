package com.ynan.test;

import com.ynan.entity.OrderInfo;
import com.ynan.service.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author yuannan
 * @Date 2021/11/8 14:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMain {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void testInsertOrderInfo() {
        orderService.selectByCondition();
    }
}
