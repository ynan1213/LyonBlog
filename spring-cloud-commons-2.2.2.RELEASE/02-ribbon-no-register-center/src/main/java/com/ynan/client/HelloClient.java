package com.ynan.client;

import com.ynan.config.MyRibbonConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @Author yuannan
 * @Date 2022/1/4 15:14
 */
@RibbonClient(name = "test-server", configuration = MyRibbonConfiguration.class)
public class HelloClient {

}
