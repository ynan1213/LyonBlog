package com.ynan._02.phase;

import org.springframework.context.annotation.Import;

/**
 * @author yuannan
 * @date 2022/11/16 10:06
 */
//@ComponentScan("com.ynan._02")
//@Import({ServiceTestB.class, ServiceTestA.class})
@Import({ServiceTestA.class, ServiceTestB.class})
public class PhaseRootConfig {

}
