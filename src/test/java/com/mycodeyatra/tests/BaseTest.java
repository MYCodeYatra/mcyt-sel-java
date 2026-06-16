package com.mycodeyatra.tests;
 
import com.mycodeyatra.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
 
public class BaseTest {
 
    protected WebDriver driver;
 
    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) {
        // Initialize the thread-safe driver
        driver = DriverFactory.initDriver(browser != null ? browser : "chrome");
        driver.manage().window().maximize();
    }
 
    @AfterMethod
    public void tearDown() {
        // Safely quit and clean up the thread
        DriverFactory.quitDriver();
    }
}