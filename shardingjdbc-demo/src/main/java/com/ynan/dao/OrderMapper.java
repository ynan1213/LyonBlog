package com.ynan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ynan.entity.OrderInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author yuannan
 * @Date 2021/11/4 21:19
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {

    // 查询某个用户订单列表
    // List<OrderInfo> queryOrderInfoList(OrderInfo orderInfo);

    // 通过订单号查询订单信息
    //OrderInfo queryOrderInfoByOrderId(OrderInfo orderInfo);

    // 插入订单信息
    //int addOrder(OrderInfo orderInfo);
}
