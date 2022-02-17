package com.ynan.mq;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @Author yuannan
 * @Date 2022/1/9 20:13
 */
@EnableBinding({XxxMessageBinding.class})
public class XxxMessageReceiver {

    Logger logger = LoggerFactory.getLogger("com.ynan.xx");

    private static int count = 0;

    //@Input 被@StreamListener注解的方法不能再被@Input注解了，否则启动时抛异常
    @StreamListener(XxxMessageBinding.INPUT)
    public void receive(@Payload Object payload, @Headers Map headers) {
//        for (Object o : headers.keySet()) {
//            System.out.println(o.toString() + " ：" + headers.get(o).toString());
//        }
        System.out.println("接收到消息体：" + payload + "    第" + (count ++) + "次消费");
        logger.info("   消息id：" + headers.get("rocketmq_MESSAGE_ID"));
        if(1== 1) throw new RuntimeException("XXX---YYY---ZZZ");
    }

    // 同一个value值可以配置在多个方法上，包括可以配置在别的类上
    // 具体见 @see com.ynan.mq.XxxMessageReceiver1
//    @StreamListener(XxxMessageBinding.INPUT)
//    public void receive1(@Payload Object payload, @Headers Map headers) {
//        for (Object o : headers.keySet()) {
//            System.out.println(o.toString() + " ：" + headers.get(o).toString());
//        }
//        System.out.println("111111 接收到消息体：" + payload);
//    }
}