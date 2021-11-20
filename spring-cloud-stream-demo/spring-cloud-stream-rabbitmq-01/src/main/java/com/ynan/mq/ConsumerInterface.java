package com.ynan.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @Author yuannan
 * @Date 2021/11/20 11:44
 */
public interface ConsumerInterface {

    static final String CONSUMER_01 = "CONSUMER-01";
    static final String CONSUMER_02 = "CONSUMER-02";

    @Input(CONSUMER_01)
    MessageChannel consumer01();

    @Input(CONSUMER_02)
    MessageChannel consumer02();

}
