package com.ynan.mq;

import com.ynan.aspect.GrayStreamListener;
import java.util.Map;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2021/11/22 15:52
 */
@Component
public class XxxMessageReceiver {

    @GrayStreamListener(value = XxxMessageBinding.INPUT, grayType = 123, grayCondition = "abc-xxy")
    public void receive(@Payload Object payload, @Headers Map headers) {
        for (Object o : headers.keySet()) {
            System.out.println(o.toString() + " ：" + headers.get(o).toString());
        }
        System.out.println("接收到消息体：" + payload);
    }
}
