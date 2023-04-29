package com.ynan.config;

import com.ynan.service.TwoServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yuannan
 */
@Configuration
@Import({ImportBean.class, TwoServiceImpl.class})
public class DependConfig {



}
