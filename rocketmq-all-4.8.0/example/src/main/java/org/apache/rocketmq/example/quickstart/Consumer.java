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

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * This example shows how to subscribe and consume messages using providing {@link DefaultMQPushConsumer}.
 */
public class Consumer
{
    public static void main(String[] args) throws InterruptedException, MQClientException
    {

        System.setProperty("rocketmq.client.logUseSlf4j", "true");

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ns02", "consume-group-001");
        //DefaultMQPullConsumer pullConsumer = new DefaultMQPullConsumer();

        // 消费组模式，集群还是广播，默认集群
        consumer.setMessageModel(MessageModel.CLUSTERING);

        consumer.setNamesrvAddr("127.0.0.1:9876");

        // 默认是 CONSUME_FROM_LAST_OFFSET
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 从指定时间错开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        // 必须是按照下面格式   年月日时分秒 如：20200722110701
        consumer.setConsumeTimestamp("20200722110701");


        // 可以订阅多个
        consumer.subscribe("TOPIC_01", "*");
        consumer.subscribe("TOPIC_02", "*");

        // 取消主题订阅
        // consumer.unsubscribe("TOPIC_02");

        // 注册消息消费钩子函数
        //consumer.getDefaultMQPushConsumerImpl().registerConsumeMessageHook(hook)

        // 并发消费
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
            System.out.println("消息条数：" + msgs.size());
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });

        // 顺序消费
        // consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
        //     return ConsumeOrderlyStatus.SUCCESS;
        // });

        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
