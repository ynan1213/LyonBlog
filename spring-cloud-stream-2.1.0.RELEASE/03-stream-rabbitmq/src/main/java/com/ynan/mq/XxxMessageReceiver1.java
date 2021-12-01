package com.ynan.mq;

import java.util.Map;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2021/11/19 11:56
 */
@Component
public class XxxMessageReceiver1 {

    @StreamListener(value = XxxMessageBinding.INPUT)
    public void receive(@Payload Object payload, @Headers Map headers) {
        for (Object o : headers.keySet()) {
            System.out.println(o.toString() + " ：" + headers.get(o).toString());
        }
        System.out.println("XxxMessageReceiver1类 接收到消息体：" + payload);
    }

    // ooo-input 不存在，初始化过程中是会报错的，因为会通过 000
    //    @StreamListener("ooo-input")
    //    public void receive1(@Payload Object payload, @Headers Map headers) {
    //        for (Object o : headers.keySet()) {
    //            System.out.println(o.toString() + " ：" + headers.get(o).toString());
    //        }
    //        System.out.println("ooo 接收到消息体：" + payload);
    //    }
}
