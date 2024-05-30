package com.ynan;

import org.apache.log4j.MDC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestAsyncLogger {

    public static void main(String[] args) {

        System.setProperty("log4j2.debug", "true");
        System.setProperty("log4j.configurationFile", "log4j2-async.xml");
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

        MDC.put("yuan", "nan");
        MDC.put("traceId", "111111111122222222");

        Logger logger = LogManager.getLogger("com.ynan.xxx.yyy");
        logger.info("xxx");
    }

}
