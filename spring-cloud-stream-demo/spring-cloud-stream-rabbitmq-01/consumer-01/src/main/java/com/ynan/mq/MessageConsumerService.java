package com.ynan.mq;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * @Author yuannan
 * @Date 2021/11/20 12:31
 */
@Service
public class MessageConsumerService {

    @StreamListener(ConsumerInterface.CONSUMER_01)
    public void consumer(Object payload) {
        System.out.println("method consumer01:" + payload.toString());
    }

}
