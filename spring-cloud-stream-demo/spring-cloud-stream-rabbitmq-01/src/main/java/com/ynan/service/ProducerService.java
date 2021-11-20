package com.ynan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * @Author yuannan
 * @Date 2021/11/20 11:51
 */
@Service
public class ProducerService {

    @Autowired(required = false)
    @Qualifier(Source.OUTPUT)
    private MessageChannel producer;

    public void say(String s) {
        for (int i = 0; i < 100; i++) {
            producer.send(MessageBuilder.withPayload(s + i).build());
        }
    }

}
