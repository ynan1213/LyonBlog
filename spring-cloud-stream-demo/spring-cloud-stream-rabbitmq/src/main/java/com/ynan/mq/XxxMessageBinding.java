package com.ynan.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author yuannan
 * @Date 2021/11/9 10:01
 */
public interface OutputMessageBinding {

    String OUTPUT = "channel-out";

    String INPUT = "channel-input";

    @Output(OUTPUT)
    MessageChannel xxxxxoutput();

    @Input(INPUT)
    SubscribableChannel xxxxxinput();
}
