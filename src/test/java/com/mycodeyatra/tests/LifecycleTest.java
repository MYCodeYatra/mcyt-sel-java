package com.mycodeyatra.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LifecycleTest {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.out.println("[Lifecycle] Launching ChromeDriver...");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
    }

    @Test
    public void executeTest() {
        System.out.println("[Lifecycle] Navigating to Practice Site...");
        driver.get("https://practice.mycodeyatra.com/");
        System.out.println("[Lifecycle] Active Page Title: " + driver.getTitle());
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            System.out.println("[Lifecycle] Destroying browser session (driver.quit)...");
            driver.quit();
        }
    }
}
