package com.ynan.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ynan.dao.OrderMapper;
import com.ynan.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author yuannan
 * @Date 2021/11/8 14:26
 */
@Service
public class OrderServiceImpl {

    @Autowired
    private OrderMapper orderMapper;

    public void selectByCondition() {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper();
        wrapper.eq(OrderInfo::getUserId, 11);
        wrapper.eq(OrderInfo::getUserName, "zhangsan");
        OrderInfo orderInfo = orderMapper.selectOne(wrapper);
        System.out.println(orderInfo);
    }

}
