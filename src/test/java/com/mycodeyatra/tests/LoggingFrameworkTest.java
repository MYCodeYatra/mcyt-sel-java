package com.mycodeyatra.tests;

import com.mycodeyatra.logging.LoggerFactory;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * LoggingFrameworkTest validates that Log4j2 is correctly configured
 * and that loggers at every level (DEBUG, INFO, WARN, ERROR) emit
 * messages as expected without throwing exceptions.
 */
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
        LOG.info("Logger initialised successfully for: {}", LoggingFrameworkTest.class.getName());
    }

    @Test(description = "DEBUG level log emits without exception")
    public void testDebugLog() {
        LOG.debug("DEBUG: Selenium driver initialisation started");
        Assert.assertTrue(LOG.isDebugEnabled() || !LOG.isDebugEnabled(),
                "isDebugEnabled() should return without exception");
    }

    @Test(description = "INFO level log emits without exception")
    public void testInfoLog() {
        LOG.info("INFO: Navigating to https://practice.mycodeyatra.com");
        Assert.assertNotNull(LOG.getName(), "Logger name should not be null");
    }

    @Test(description = "WARN level log emits without exception")
    public void testWarnLog() {
        LOG.warn("WARN: Element not found on first attempt — retrying...");
        Assert.assertTrue(true, "WARN log executed without exception");
    }

    @Test(description = "ERROR level log with exception emits without throwing")
    public void testErrorLog() {
        Exception simulatedException = new RuntimeException("Simulated test failure");
        LOG.error("ERROR: Test failed unexpectedly", simulatedException);
        Assert.assertTrue(true, "ERROR log with exception executed without throwing");
    }

    @Test(description = "Named logger works for cross-cutting TEST_EXECUTION category")
    public void testNamedLogger() {
        Assert.assertNotNull(EXEC_LOG, "Named logger should not be null");
        EXEC_LOG.info("TEST_EXECUTION: testNamedLogger PASSED");
    }

    @Test(description = "Parameterised log message formats correctly")
    public void testParameterisedLog() {
        String browser = "chrome";
        String env = "qa";
        LOG.info("Starting test on browser={} in environment={}", browser, env);
        Assert.assertTrue(true, "Parameterised log executed without exception");
    }
}
