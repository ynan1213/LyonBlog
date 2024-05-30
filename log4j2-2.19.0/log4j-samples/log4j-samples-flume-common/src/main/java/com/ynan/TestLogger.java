package com.ynan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.log4j.MDC;

public class TestLogger {

    public static void main(String[] args) {

        System.setProperty("log4j2.debug", "true");

        Logger statusLogger = StatusLogger.getLogger();
        statusLogger.error("error -------");
        statusLogger.info("info -------");
        statusLogger.debug("debug -------");

        MDC.put("yuan", "nan");
        MDC.put("traceId", "111111111122222222");

        Logger logger = LogManager.getLogger("com.ynan.xxx.yyy");
        logger.info("xxx");

    }

}
