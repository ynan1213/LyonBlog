package com.ynan.mq;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @Author yuannan
 * @Date 2021/11/20 11:43
 */
@EnableBinding({Source.class})
public class XxxMessageProducer {

//    @StreamListener(ConsumerInterface.CONSUMER_01)
//    public void consumer01(Object payload) {
//        System.out.println("method consumer01:" + payload.toString());
//    }
//
//    @StreamListener(ConsumerInterface.CONSUMER_02)
//    public void consumer02(Object payload) {
//        System.out.println("method consumer02:" + payload.toString());
//    }

}
