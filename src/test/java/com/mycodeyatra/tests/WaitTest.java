package com.mycodeyatra.tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
public class WaitTest {
    private WebDriver driver;
    private WebDriverWait explicitWait;
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        // 1. Implicit Wait Configuration (10 seconds fallback)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // 2. Explicit Wait Configuration (5 seconds timeout)
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    @Test
    public void testWaitStrategies() {
        System.out.println("Navigating to: https://practice.mycodeyatra.com/");
        driver.get("https://practice.mycodeyatra.com/");
        System.out.println("Opening Sandbox Arena...");
        WebElement sandboxLink = explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Sandbox Arena')]")));
        sandboxLink.click();
        System.out.println("Opening Dynamic Content & Loading Tile...");
        WebElement dynamicContentTile = explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[text()='Dynamic Content']")));
        dynamicContentTile.click();
        // --- PHASE 1: EXPLICIT WAIT DEMO ---
        System.out.println("--- Starting Explicit Wait Demo ---");
        WebElement startLoadingBtn = driver.findElement(By.xpath("//button[@data-testid='start-loading-btn']"));
        startLoadingBtn.click();
        // Wait for the delayed content element to appear (expected delay 3s)
        System.out.println("Waiting for delayed content to appear (max 5s)...");
        WebElement delayedContent = explicitWait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid='delayed-content']"))
        );
        String contentText = delayedContent.getText();
        System.out.println("Delayed Content loaded: " + contentText);
        Assert.assertEquals(contentText, "Content loaded after 3 seconds!", "Failed to fetch delayed content.");
        // --- PHASE 2: FLUENT WAIT DEMO ---
        System.out.println("--- Starting Fluent Wait Demo ---");
        WebElement startProgressBtn = driver.findElement(By.xpath("//button[@data-testid='start-progress-btn']"));
        startProgressBtn.click();
        // Configure Fluent Wait: 10s timeout, 500ms polling, ignore NoSuchElementException
        System.out.println("Configuring Fluent Wait (timeout 10s, polling 500ms)...");
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        // Wait for progress text to reach 100%
        WebElement progressText = fluentWait.until(d -> {
            WebElement element = d.findElement(By.xpath("//strong[@data-testid='progress-text']"));
            String progressVal = element.getText();
            System.out.println("Fluent polling progress: " + progressVal);
            if ("100%".equals(progressVal)) {
                return element;
            }
            return null;
        });
        Assert.assertNotNull(progressText, "Progress failed to reach 100% within timeout.");
        // Click completed progress button
        WebElement progressCompleteBtn = driver.findElement(By.xpath("//button[@data-testid='progress-complete-btn']"));
        Assert.assertTrue(progressCompleteBtn.isEnabled(), "Complete button is still disabled.");
        progressCompleteBtn.click();
        System.out.println("Progress button clicked successfully!");
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}