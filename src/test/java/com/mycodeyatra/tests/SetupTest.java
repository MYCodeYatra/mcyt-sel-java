package com.mycodeyatra.tests;

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

public class SetupTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        // Configure ChromeOptions to run headlessly
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");

        // Initialize ChromeDriver (Selenium Manager discovers the binary automatically)
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    public void testProjectSetup() {
        // 1. Navigate to the Live Practice Site
        System.out.println("Navigating to: https://practice.mycodeyatra.com/");
        driver.get("https://practice.mycodeyatra.com/");

        // 2. Open Sandbox Arena Page
        System.out.println("Navigating to Sandbox Arena...");
        WebElement sandboxLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Sandbox Arena')]")));
        sandboxLink.click();

        // 3. Click the Form Practice Tile Card
        System.out.println("Clicking Form Practice Tile...");
        WebElement formTile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[text()='Form Practice']")));
        formTile.click();

        // 4. Validate the Form Header Text
        WebElement formHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Form Submission')]")));
        String headerText = formHeader.getText();
        System.out.println("Loaded Page Header: " + headerText);
        Assert.assertEquals(headerText, "Form Submission & Data Validation", "Failed to navigate to Form Practice page.");
    }

    @AfterMethod
    public void tearDown() {
        // Clean up the driver instance
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}
