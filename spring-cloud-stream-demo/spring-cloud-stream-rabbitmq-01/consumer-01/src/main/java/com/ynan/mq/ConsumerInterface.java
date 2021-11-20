package com.ynan.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @Author yuannan
 * @Date 2021/11/20 12:28
 */
public interface ConsumerInterface {

    static final String CONSUMER_01 = "CONSUMER-01";

    @Input(CONSUMER_01)
    MessageChannel consumer01();

}
