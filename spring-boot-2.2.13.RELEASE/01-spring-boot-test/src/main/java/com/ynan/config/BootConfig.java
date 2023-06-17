package com.ynan.config;

import com.xxx.SeqServiceImpl1;
import com.xxx.SeqServiceImpl2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yuannan
 */
@Configuration(proxyBeanMethods = false)
@Import({SeqServiceImpl1.class, SeqServiceImpl2.class})
public class BootConfig {

}
