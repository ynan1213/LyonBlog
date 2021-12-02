package com.ynan.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

/**
 * @Author yuannan
 * @Date 2021/12/2 23:11
 */
@Slf4j
public class MyCustomLog implements Log {

    public MyCustomLog(String xxx) {
        System.out.println(xxx);
        // Do Nothing
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s, e);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }

    @Override
    public void trace(String s) {
        log.trace(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }
}