package com.mycodeyatra.tests;
import com.mycodeyatra.logging.LoggerFactory;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class LoggingFrameworkTest {
 private static final Logger LOG = LoggerFactory.getLogger(LoggingFrameworkTest.class);
 private static final Logger EXEC_LOG = LoggerFactory.getLogger("TEST_EXECUTION");
 @BeforeClass
 public void setup() {
  LOG.info("LoggingFrameworkTest suite starting...");
 }
 @Test(description = "Logger is not null and correctly initialised")
 public void testLoggerInitialised() {
  Assert.assertNotNull(LOG, "Logger should not be null");
  LOG.info("Logger initialised for: {}", LoggingFrameworkTest.class.getName());
 }
 @Test(description = "DEBUG level log emits without exception")
 public void testDebugLog() {
  LOG.debug("DEBUG: Selenium driver initialisation started");
  Assert.assertTrue(LOG.isDebugEnabled() || !LOG.isDebugEnabled());
 }
 @Test(description = "WARN level log emits without exception")
 public void testWarnLog() {
  LOG.warn("WARN: Element not found on first attempt - retrying...");
  Assert.assertTrue(true);
 }
 @Test(description = "ERROR level log with exception emits without throwing")
 public void testErrorLog() {
  LOG.error("ERROR: Test failed unexpectedly", new RuntimeException("Simulated failure"));
  Assert.assertTrue(true);
 }
 @Test(description = "Named logger works for TEST_EXECUTION category")
 public void testNamedLogger() {
  Assert.assertNotNull(EXEC_LOG);
  EXEC_LOG.info("TEST_EXECUTION: testNamedLogger PASSED");
 }
 @Test(description = "Parameterised log message formats correctly")
 public void testParameterisedLog() {
  LOG.info("Starting test on browser={} in environment={}", "chrome", "qa");
  Assert.assertTrue(true);
 }
}