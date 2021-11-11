package com.ynan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author yuannan
 * @Date 2021/11/3 10:45
 */
@Configuration
@Import(BeanImportTest.class)
public class RootConfig1 {

}
