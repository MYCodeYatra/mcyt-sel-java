package com.mycodeyatra.tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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
import java.util.logging.Level;
public class CdpTest {
    private WebDriver driver;
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new"); // Keep execution clean in CI pipelines
        // 1. Enable Browser console logging preferences
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
        // 2. Inject custom console messages via JavaScript to simulate app errors
        System.out.println("Injecting custom console warning and error messages...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("console.warn('CDP Test: This is a custom warning message');");
        js.executeScript("console.error('CDP Test: This is a custom error message');");
        // 3. Fetch browser console log entries
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
        // 4. Assert that the logs were successfully captured
        Assert.assertTrue(foundWarning, "Injected console warning was not captured.");
        Assert.assertTrue(foundError, "Injected console error was not captured.");
        System.out.println("CDP console logs validation complete!");
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}