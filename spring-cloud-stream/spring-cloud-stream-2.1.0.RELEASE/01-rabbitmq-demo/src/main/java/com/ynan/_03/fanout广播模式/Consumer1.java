package com.ynan._03.fanout广播模式;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.ynan.util.ConnectionUtils;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author yuannan
 * @Date 2021/11/7 22:57
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection("消费者");
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("logs", "fanout");
        String queue = channel.queueDeclare().getQueue();

        channel.queueBind(queue, "logs", "", null);

        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1：" + new String(body));
            }
        });
    }
}
