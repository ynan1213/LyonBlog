package com.ynan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ynan.entity.User;

/**
 * @Author yuannan
 * @Date 2021/11/8 14:24
 */
//@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 查询某个用户订单列表
    // List<User> queryOrderInfoList(User orderInfo);

    // 通过订单号查询订单信息
    //User queryOrderInfoByOrderId(User orderInfo);

    // 插入订单信息
    //int addOrder(User orderInfo);
}
