package com.mycodeyatra.tests;

import com.mycodeyatra.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DriverFactoryTest {

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        DriverFactory.initDriver(browser);
    }

    @Test
    public void testDriverFactoryChrome() {
        WebDriver driver = DriverFactory.getDriver();
        Assert.assertNotNull(driver, "Driver instance should not be null!");
        
        driver.get("https://practice.mycodeyatra.com/#/login");
        String title = driver.getTitle();
        System.out.println("[DriverFactoryTest] Current page title: " + title);
        Assert.assertTrue(title.contains("MyCodeYatra"), "Title should contain MyCodeYatra!");
    }

    @Test
    public void testThreadSafety() {
        long currentThreadId = Thread.currentThread().getId();
        WebDriver driver1 = DriverFactory.getDriver();
        System.out.println("[DriverFactoryTest] Thread ID: " + currentThreadId + " - WebDriver: " + driver1);
        Assert.assertNotNull(driver1, "Driver on current thread should be active.");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
