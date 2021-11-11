package com.ynan.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author yuannan
 * @Date 2021/11/4 21:18
 */
@TableName("t_order")
public class OrderInfo {

    private String id;
    private Long userId;
    private Long orderId;
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
