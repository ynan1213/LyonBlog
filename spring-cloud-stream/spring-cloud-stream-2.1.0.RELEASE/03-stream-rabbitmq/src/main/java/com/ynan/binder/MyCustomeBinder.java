package com.ynan.binder;

import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.messaging.MessageChannel;

/**
 * @Author yuannan
 * @Date 2021/11/15 15:03
 */
public class MyCustomeBinder implements Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

    @Override
    public Binding<MessageChannel> bindConsumer(String name, String group, MessageChannel inboundBindTarget, ConsumerProperties consumerProperties) {
        return null;
    }

    @Override
    public Binding<MessageChannel> bindProducer(String name, MessageChannel outboundBindTarget, ProducerProperties producerProperties) {
        return null;
    }
}
