package com.ynan._01.env;

import org.springframework.context.annotation.Import;

/**
 * @author yuannan
 * @date 2022/11/16 09:43
 */
@Import({Service1.class, Service2.class})
public class RootConfig {

}
