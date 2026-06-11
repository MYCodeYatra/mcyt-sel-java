package com.mycodeyatra.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
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

public class AlertTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    public void testJavaScriptAlerts() {
        System.out.println("Navigating to: https://practice.mycodeyatra.com/");
        driver.get("https://practice.mycodeyatra.com/");

        System.out.println("Opening Sandbox Arena...");
        WebElement sandboxLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Sandbox Arena')]")));
        sandboxLink.click();

        System.out.println("Opening Alerts & Overlays Page...");
        WebElement overlaysTile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[text()='Alerts & Overlays']")));
        overlaysTile.click();

        // 1. Native Alert Handler
        System.out.println("Testing Standard JavaScript Alert...");
        driver.findElement(By.xpath("//button[@data-testid='alert-btn']")).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        System.out.println("Alert Text Captured: " + alertText);
        Assert.assertEquals(alertText, "This is a standard JavaScript Alert!");
        alert.accept();
        
        WebElement resultText = driver.findElement(By.xpath("//div[@data-testid='alert-result']"));
        System.out.println("Result Text: " + resultText.getText());
        Assert.assertTrue(resultText.getText().contains("Alert dismissed"));

        // 2. Native Confirm Handler - Accept (OK)
        System.out.println("Testing Confirm Alert - Clicking OK...");
        driver.findElement(By.xpath("//button[@data-testid='confirm-btn']")).click();
        alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "Do you want to proceed?");
        alert.accept();
        System.out.println("Result Text: " + resultText.getText());
        Assert.assertTrue(resultText.getText().contains("You clicked OK"));

        // 3. Native Confirm Handler - Dismiss (Cancel)
        System.out.println("Testing Confirm Alert - Clicking Cancel...");
        driver.findElement(By.xpath("//button[@data-testid='confirm-btn']")).click();
        alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
        System.out.println("Result Text: " + resultText.getText());
        Assert.assertTrue(resultText.getText().contains("You clicked Cancel"));

        // 4. Native Prompt Handler
        System.out.println("Testing Prompt Alert - Sending Input...");
        driver.findElement(By.xpath("//button[@data-testid='prompt-btn']")).click();
        alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "Please enter your name:");
        alert.sendKeys("Pankaj");
        alert.accept();
        System.out.println("Result Text: " + resultText.getText());
        Assert.assertTrue(resultText.getText().contains("Hello, Pankaj!"));
        
        System.out.println("JavaScript alerts test validations complete!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}
