package com.ynan._02.work队列模式;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ynan.util.ConnectionUtils;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author yuannan
 * @Date 2021/11/7 21:57
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("HelloWorld", true, false, false, null);
        for (int i = 0; i < 20; i++) {
            channel.basicPublish("", "HelloWorld", null, (i + "hello world !!!").getBytes());
        }
        System.out.println("发送消息成功");

        channel.close();
        connection.close();
    }
}
