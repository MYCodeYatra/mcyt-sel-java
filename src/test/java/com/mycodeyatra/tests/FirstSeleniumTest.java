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
public class FirstSeleniumTest {
    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeMethod
    public void setUp() {
        // Setup ChromeDriver binary automatically
        WebDriverManager.chromedriver().setup();
        // Configure options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        // Initialize driver
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Initialize explicit wait (5 seconds timeout)
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    @Test
    public void testFirstScript() {
        // 1. Navigate to the Live Practice Site
        System.out.println("Navigating to: https://practice.mycodeyatra.com/");
        driver.get("https://practice.mycodeyatra.com/");
        // 2. Validate Homepage Title
        String homeTitle = driver.getTitle();
        System.out.println("Homepage Title: " + homeTitle);
        Assert.assertTrue(homeTitle.contains("MyCodeYatra") || homeTitle.isEmpty(), "Title validation failed");
        // 3. Click Sandbox Arena button
        System.out.println("Opening Sandbox Arena...");
        WebElement sandboxLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Sandbox Arena')]")));
        sandboxLink.click();
        // 4. Validate Sandbox Header using explicit wait to prevent race condition
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Test Practice Sandbox')]")));
        String headerText = header.getText();
        System.out.println("Sandbox Page Header: " + headerText);
        Assert.assertTrue(headerText.contains("Test Practice Sandbox"), "Failed to load Sandbox Arena page");
    }
    @AfterMethod
    public void tearDown() {
        // Close driver session safely
        if (driver != null) {
            System.out.println("Quitting browser driver session...");
            driver.quit();
        }
    }
}