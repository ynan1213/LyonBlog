package com.ynan.service;

import com.ynan.dao.OrderMapper;
import com.ynan.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author yuannan
 * @Date 2021/11/4 21:19
 */
@Service
public class OrderServiceImpl {

    @Autowired
    private OrderMapper orderMapper;

    //    public List<OrderInfo> queryOrderInfoList(OrderInfo orderInfo) {
    //        return orderMapper.queryOrderInfoList(orderInfo);
    //    }

    public OrderInfo queryOrderInfoByOrderId(OrderInfo orderInfo) {
        // return orderMapper.queryOrderInfoByOrderId(orderInfo);
        return orderMapper.selectById(orderInfo.getOrderId());
    }

    public int addOrder(OrderInfo orderInfo) {
        //        return orderMapper.addOrder(orderInfo);
        return orderMapper.insert(orderInfo);
    }

}
