package com.ynan.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author yuannan
 * @Date 2021/11/9 10:01
 */
public interface XxxMessageBinding {

    String OUTPUT = "xxx-output";

    String INPUT = "xxx-input";

    // 如果没有指定，默认就是方法名
    @Output(OUTPUT)
    MessageChannel xxxOutput();

    @Input(INPUT)
    SubscribableChannel xxxInput();

    @Output
    MessageChannel yyyOutput();
}
