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

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * This class demonstrates how to send messages to brokers using provided {@link DefaultMQProducer}.
 */
public class Producer
{
    public static void main(String[] args) throws MQClientException, InterruptedException
    {
        DefaultMQProducer producer = new DefaultMQProducer("ns01", "please_rename_unique_group_name");

        //producer.setNamespace("");
        //producer.setProducerGroup("producerGroup");

        //producer.setUnitName("ccc");

        //producer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
        producer.setNamesrvAddr("127.0.0.1:9876");

        //producer.setClientIP("127.0.0.1");
        //producer.setSendMsgTimeout(6000);
        //producer.setPollNameServerInterval(30000);
        //producer.setHeartbeatBrokerInterval(30000);

        //producer.setDefaultTopicQueueNums(6);
        //producer.setRetryTimesWhenSendAsyncFailed();

        producer.start();

        for (int i = 0; i < 1; i++)
        {
            try
            {
                Message msg = new Message("topic000001", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

                // key用于建立索引，之后可以通过命令工具/API/或者管理平台查询key，可以为一个消息设置多个key，用空格""进行分割
                // key存在properties中，
                msg.setKeys("hello");

                // 延迟消息
                //msg.setDelayTimeLevel(2);

                // 同步发送
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);

                // 异步发送
                // producer.send(msg, new SendCallback()
                // {
                //     @Override
                //     public void onSuccess(SendResult sendResult)
                //     {
                //
                //     }
                //
                //     @Override
                //     public void onException(Throwable e)
                //     {
                //
                //     }
                // });

                // 单向发送
                //producer.sendOneway(msg);

                // 事物消息
                //producer.sendMessageInTransaction()

                // 批量消息
                //producer.send()

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        producer.shutdown();
    }
}