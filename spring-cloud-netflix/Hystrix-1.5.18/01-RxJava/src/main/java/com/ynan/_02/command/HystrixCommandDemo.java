package com.ynan._02.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * @Author yuannan
 * @Date 2022/2/11 17:38
 */
public class HystrixCommandDemo extends HystrixCommand<String> {

    private int num;

    static HystrixCommand.Setter CIRCUITBREAKER = HystrixCommand.Setter
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("HystrixService"))
        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
            .withCircuitBreakerEnabled(true)
            .withCircuitBreakerRequestVolumeThreshold(5)
            .withCircuitBreakerErrorThresholdPercentage(50)
            .withCircuitBreakerSleepWindowInMilliseconds(5000)
        );

    public HystrixCommandDemo(int num) {
        super(CIRCUITBREAKER);
        this.num = num;
    }

    @Override
    protected String run() throws Exception {
        if (num % 2 == 0) {
            return "正常访问";
        }
        throw new RuntimeException("");
    }

    @Override
    protected String getFallback() {
        return "熔断降级";
    }
}
