package com.mycodeyatra.factory;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
 
public class DriverFactory {
 
    // ThreadLocal wrapper around the WebDriver
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
 
    /**
     * Initializes the WebDriver and assigns it to the current thread.
     */
    public static WebDriver initDriver(String browser) {
        WebDriver driver;
        if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver();
        }
 
        // Set the driver into the ThreadLocal map
        tlDriver.set(driver);
        return getDriver();
    }
 
    /**
     * Retrieves the WebDriver assigned to the current executing thread.
     */
    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }
 
    /**
     * Quits the driver and clears the ThreadLocal memory to prevent memory leaks.
     */
    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}