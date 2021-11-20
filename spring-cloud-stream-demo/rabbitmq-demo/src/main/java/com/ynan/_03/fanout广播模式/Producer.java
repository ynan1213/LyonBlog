package com.ynan._03.fanout广播模式;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ynan.util.ConnectionUtils;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author yuannan
 * @Date 2021/11/7 22:52
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();


        channel.exchangeDeclare("logs", "fanout");

        for (int i = 0; i < 6; i++) {
            channel.basicPublish("logs", "", null, (i + "xxxxxx !!!").getBytes());
        }
        System.out.println("发送消息成功");

        channel.close();
        connection.close();
    }
}
