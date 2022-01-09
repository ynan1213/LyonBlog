package com.ynan._01.简单队列模式;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ynan.util.ConnectionUtils;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author yuannan
 * @Date 2021/11/7 20:57
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection("生产者");
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("hello", "fanout", true, false, false, null);

        //channel.queueDeclare("HelloWorld", false, false, false, null);

        channel.basicPublish("", "HelloWorld222", null, "hello world !!!".getBytes());
        System.out.println("发送消息成功");

        channel.close();
        connection.close();
    }

}
