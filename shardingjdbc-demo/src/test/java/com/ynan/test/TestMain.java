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
 * @Date 2021/11/4 21:21
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMain {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void testInsertOrderInfo() {
        for (int i = 0; i < 1000; i++) {
            long userId = i;
            long orderId = i + 1;
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserName("snowalker" + i);
            orderInfo.setUserId(userId);
            orderInfo.setOrderId(orderId);
            orderInfo.setId(i + "");
            int result = orderService.addOrder(orderInfo);
            if (1 == result) {
                System.out.println("入库成功,orderInfo=" + orderInfo);
            } else {
                System.out.println("入库失败,orderInfo=" + orderInfo);
            }
        }
    }
}