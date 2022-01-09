package com.ynan._01.简单队列模式;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ynan.util.ConnectionUtils;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author yuannan
 * @Date 2021/11/7 21:28
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection("消费者");
        Channel channel = connection.createChannel();

//        channel.queueDeclare("HelloWorld111", false, false, false, null);
//        channel.queueDeclare("HelloWorld222", false, false, false, null);
//        channel.queueDeclare("HelloWorld333", false, false, false, null);
//
//        channel.queueBind("HelloWorld111", "hello", "key111");
//        channel.queueBind("HelloWorld222", "hello", "key222");
//        channel.queueBind("HelloWorld333", "hello", "key333");

        String queue = channel.queueDeclare().getQueue();
        String queue1 = channel.queueDeclare().getQueue();
        String queue2 = channel.queueDeclare().getQueue();
        System.out.println(queue);

        //        channel.basicConsume("HelloWorld111", true, new DefaultConsumer(channel) {
        //            @Override
        //            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
        //                System.out.println("消费者消费消息：" + new String(body));
        //            }
        //        });
    }

}
