package com.xxx;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * @author yuannan
 */
@ConditionalOnMissingBean(SeqService.class)
public class SeqServiceImpl2 implements SeqService{

}
