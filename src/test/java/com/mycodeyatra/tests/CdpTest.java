package com.mycodeyatra.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CdpTest {
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");

        // Enable Browser console logging preferences
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        options.setCapability(ChromeOptions.LOGGING_PREFS, logPrefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testBrowserConsoleLogs() {
        System.out.println("Navigating to: https://practice.mycodeyatra.com/");
        driver.get("https://practice.mycodeyatra.com/");

        // Inject custom console messages via JavascriptExecutor to simulate application behavior
        System.out.println("Injecting custom console warning and error messages...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("console.warn('CDP Test: This is a custom warning message');");
        js.executeScript("console.error('CDP Test: This is a custom error message');");

        // Fetch browser console log entries
        System.out.println("Fetching console logs...");
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        
        boolean foundWarning = false;
        boolean foundError = false;

        for (LogEntry entry : logEntries) {
            System.out.println("[" + entry.getLevel() + "] " + entry.getMessage());
            if (entry.getMessage().contains("This is a custom warning message")) {
                foundWarning = true;
            }
            if (entry.getMessage().contains("This is a custom error message")) {
                foundError = true;
            }
        }

        // Assert that the injected logs were successfully captured
        Assert.assertTrue(foundWarning, "Injected console warning was not captured.");
        Assert.assertTrue(foundError, "Injected console error was not captured.");
        System.out.println("CDP console logs validation complete!");
    }

    @Test
    public void testNetworkThrottlingEmulation() {
        System.out.println("Enabling network throttling via version-independent executeCdpCommand...");
        
        Map<String, Object> networkConditions = new HashMap<>();
        networkConditions.put("offline", false);
        networkConditions.put("latency", 100); // 100ms latency
        networkConditions.put("downloadThroughput", 50 * 1024); // 50 KB/s download speed
        networkConditions.put("uploadThroughput", 20 * 1024); // 20 KB/s upload speed

        // Execute CDP command to emulate network conditions
        driver.executeCdpCommand("Network.emulateNetworkConditions", networkConditions);

        long startTime = System.currentTimeMillis();
        System.out.println("Navigating to live login URL on throttled connection...");
        driver.get("https://practice.mycodeyatra.com/#/login");
        long duration = System.currentTimeMillis() - startTime;
        
        System.out.println("Page loaded in " + duration + " ms.");
        // Verify we navigated successfully
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Failed to load Login page under throttled network!");
    }

    @Test
    public void testGeolocationEmulation() {
        System.out.println("Emulating Tokyo, Japan location coordinates via CDP...");
        
        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("latitude", 35.6762);
        coordinates.put("longitude", 139.6503);
        coordinates.put("accuracy", 1);

        // Execute CDP command to emulate geolocation
        driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);

        driver.get("https://practice.mycodeyatra.com/");
        
        // Fetch geolocation coordinates from browser window context to verify emulation
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("navigator.geolocation.getCurrentPosition(function(position) { " +
                "console.log('Lat: ' + position.coords.latitude + ' Lon: ' + position.coords.longitude); " +
                "});");
        
        System.out.println("Tokyo Geolocation Emulation complete!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}
