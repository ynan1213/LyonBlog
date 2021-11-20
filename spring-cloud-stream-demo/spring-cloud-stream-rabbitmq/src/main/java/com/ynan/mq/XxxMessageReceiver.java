package com.ynan.mq;

import java.util.Map;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @Author yuannan
 * @Date 2021/11/9 10:02
 */
// 重复配置也不会被解析
@EnableBinding({XxxMessageBinding.class, XxxMessageBinding.class})
public class SinkReceiver {

    @StreamListener(XxxMessageBinding.INPUT)
    public void reveive(@Payload Object payload, @Headers Map headers) {
        for (Object o : headers.keySet()) {
            System.out.println(o.toString() + " ：" + headers.get(o).toString());
        }
        System.out.println("接收到消息体：" + payload);
    }

}
