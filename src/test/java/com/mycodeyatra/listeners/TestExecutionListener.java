package com.mycodeyatra.listeners;
 
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
 
public class TestExecutionListener implements ITestListener {
 
    private static final Logger logger = LogManager.getLogger(TestExecutionListener.class);
 
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test FAILED: {}", result.getName());
        logger.error("Reason: {}", result.getThrowable().getMessage());
 
        // Retrieve the WebDriver instance from your BaseTest or Spring Context
        Object testClass = result.getInstance();
        WebDriver driver = ((SpringBaseTest) testClass).getDriver();
 
        if (driver != null) {
            captureScreenshot(driver, result.getName());
        }
    }
 
    private void captureScreenshot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dest = Paths.get("target/screenshots/" + testName + ".png");
            Files.createDirectories(dest.getParent());
            Files.copy(src.toPath(), dest);
            logger.info("Screenshot saved to: {}", dest.toString());
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: ", e);
        }
    }
 
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: {}", result.getName());
    }
 
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test PASSED: {}", result.getName());
    }
}