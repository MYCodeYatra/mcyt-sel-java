package com.mycodeyatra.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LoggerFactory - Centralised factory for obtaining Log4j2 Logger instances.
 * Every class obtains its logger via:
 *   private static final Logger LOG = LoggerFactory.getLogger(MyClass.class);
 */
public class LoggerFactory {

    private LoggerFactory() {}

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return LogManager.getLogger(name);
    }
}
