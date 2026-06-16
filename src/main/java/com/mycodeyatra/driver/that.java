package com.mycodeyatra.driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;
/**
 * DriverFactory is a thread-safe singleton class that manages WebDriver instances
 * using ThreadLocal to support parallel and clean test execution.
 */
public class DriverFactory {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    // Private constructor to prevent direct instantiation
    private DriverFactory() {}
    /**
     * Initializes the driver session for the current thread based on the specified browser.
     * Defaults to Chrome if no browser is specified.
     * 
     * @param browser The browser name ("chrome", "firefox", "edge")
     * @return The initialized WebDriver instance
     */
    public static WebDriver initDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            WebDriver driver;
            String browserName = (browser != null) ? browser.trim().toLowerCase() : "chrome";
            System.out.println("[DriverFactory] Initializing WebDriver for browser: " + browserName);
            switch (browserName) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("-headless");
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--remote-allow-origins=*");
                    edgeOptions.addArguments("--headless");
                    edgeOptions.addArguments("--window-size=1920,1080");
                    driver = new EdgeDriver(edgeOptions);
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    driver = new ChromeDriver(chromeOptions);
                    break;
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }
    /**
     * Retrieves the thread-safe WebDriver instance for the current thread.
     * 
     * @return The WebDriver instance, or null if not initialized
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    /**
     * Quits the WebDriver instance for the current thread and removes it from ThreadLocal.
     */
    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            System.out.println("[DriverFactory] Quitting WebDriver session for thread: " + Thread.currentThread().getId());
            try {
                driverThreadLocal.get().quit();
            } catch (Exception e) {
                System.err.println("[DriverFactory] Error encountered while quitting driver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}