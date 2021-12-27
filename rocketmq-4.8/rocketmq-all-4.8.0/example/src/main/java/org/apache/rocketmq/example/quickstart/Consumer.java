/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.example.quickstart;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragelyByCircle;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * This example shows how to subscribe and consume messages using providing {@link DefaultMQPushConsumer}.
 */
public class Consumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        System.setProperty("rocketmq.client.logUseSlf4j", "true");

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xxx-group-xxx-030", true);
        consumer.setNamespace("xxx");
        //DefaultMQPullConsumer pullConsumer = new DefaultMQPullConsumer()

        // 消费组模式，集群还是广播，默认集群
        consumer.setMessageModel(MessageModel.CLUSTERING);
        //consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.setNamesrvAddr("47.100.24.106:9876");

        // 默认是 CONSUME_FROM_LAST_OFFSET
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 从指定时间戳开始消费
        // consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        // 必须是按照下面格式   年月日时分秒 如：20200722110701
        // consumer.setConsumeTimestamp("20200722110701");

        // 可以订阅多个
        // subExpression 可以填多个，用 || 分隔
        consumer.subscribe("request_topic111", "*");

        // 取消主题订阅
        // consumer.unsubscribe("TOPIC_02");

        // 注册队列分配策略器
        consumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragelyByCircle());

        // 注册消息消费钩子函数
        //consumer.getDefaultMQPushConsumerImpl().registerConsumeMessageHook(hook)

        // 每次消费的条数，默认1条，也就是下面回调方法 消息条数
        //consumer.setConsumeMessageBatchMaxSize();

        // 默认为-1，重新消费次数，什么时候生效呢？
        // 并发模式下：在消费消息返回LATER然后返回ACK消息给broker时会带上，在发送的时候会被默认设置为16 @see DefaultMQPushConsumerImpl.sendMessageBack
        // 顺序模式下，是Integer.MAX_VALUE
        consumer.setMaxReconsumeTimes(3);

        // 并发消费
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            MessageExt messageExt = msgs.get(0);
            System.out.println("--------------");
            System.out.println(messageExt.getProperties());
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        AtomicInteger i = new AtomicInteger(0);
        //顺序消费
//        consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
//            System.out.println("-------- 第 " + i.incrementAndGet() + "次消费 ---------------");
//            return ConsumeOrderlyStatus.SUCCESS;
//        });

        consumer.start();

    }
}
