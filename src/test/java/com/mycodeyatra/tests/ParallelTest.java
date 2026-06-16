package com.mycodeyatra.tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
public class ParallelTest {
    // ThreadLocal WebDriver to ensure thread-safety during parallel runs
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverThreadLocal.set(driver);
    }
    @Test
    public void testParallelLogin() {
        WebDriver driver = getDriver();
        long threadId = Thread.currentThread().getId();
        System.out.println("Thread ID: " + threadId + " - Navigating to Login Page");
        driver.get("https://practice.mycodeyatra.com/#/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-testid='username']")));
        Assert.assertNotNull(usernameInput);
        System.out.println("Thread ID: " + threadId + " - Login Page Verified successfully!");
    }
    @Test
    public void testParallelForm() {
        WebDriver driver = getDriver();
        long threadId = Thread.currentThread().getId();
        System.out.println("Thread ID: " + threadId + " - Navigating to Form Practice Page");
        driver.get("https://practice.mycodeyatra.com/#/form-practice");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-testid='full-name']")));
        Assert.assertNotNull(nameInput);
        System.out.println("Thread ID: " + threadId + " - Form Practice Page Verified successfully!");
    }
    @Test
    public void testParallelSandbox() {
        WebDriver driver = getDriver();
        long threadId = Thread.currentThread().getId();
        System.out.println("Thread ID: " + threadId + " - Navigating to Sandbox Page");
        driver.get("https://practice.mycodeyatra.com/#/sandbox");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sandboxHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2")));
        Assert.assertTrue(sandboxHeader.getText().contains("Sandbox"));
        System.out.println("Thread ID: " + threadId + " - Sandbox Page Verified successfully!");
    }
    @AfterMethod
    public void tearDown() {
        WebDriver driver = getDriver();
        long threadId = Thread.currentThread().getId();
        if (driver != null) {
            System.out.println("Thread ID: " + threadId + " - Quitting Driver Session");
            driver.quit();
        }
        driverThreadLocal.remove();
    }
}