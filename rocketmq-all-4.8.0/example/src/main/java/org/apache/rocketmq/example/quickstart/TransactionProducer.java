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
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * This class demonstrates how to send messages to brokers using provided {@link DefaultMQProducer}.
 */
public class TransactionProducer
{
    public static void main(String[] args) throws MQClientException, InterruptedException
    {
        TransactionMQProducer producer = new TransactionMQProducer("ns01", "please_rename_unique_group_name");

        producer.setNamesrvAddr("127.0.0.1:9876");

        producer.setTransactionListener(new TransactionListener()
        {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg)
            {
                System.out.println("执行本地事物");
                return null;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg)
            {
                System.out.println("执行消息回查");
                return null;
            }
        });

        producer.start();

        for (int i = 0; i < 1; i++)
        {
            try
            {
                Message msg = new Message("topic000001", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

                TransactionSendResult sendResult = producer.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //producer.shutdown();
    }
}
