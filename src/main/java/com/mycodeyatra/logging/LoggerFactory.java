package com.mycodeyatra.logging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class LoggerFactory {
 private LoggerFactory() {}
 public static Logger getLogger(Class<?> clazz) {
  return LogManager.getLogger(clazz);
 }
 public static Logger getLogger(String name) {
  return LogManager.getLogger(name);
 }
}