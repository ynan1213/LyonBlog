package com.ynan.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @Author yuannan
 * @Date 2021/11/20 12:30
 */
public interface ConsumerInterface {

    static final String CONSUMER_02 = "CONSUMER-02";

    @Input(CONSUMER_02)
    MessageChannel consumer02();
}
