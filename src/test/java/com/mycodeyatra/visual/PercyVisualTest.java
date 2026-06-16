package com.mycodeyatra.visual;
 
import io.percy.selenium.Percy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
 
import java.util.Arrays;
 
public class PercyVisualTest {
 
    private WebDriver driver;
    private Percy percy;
 
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
 
        // Initialize the Percy SDK with your WebDriver
        percy = new Percy(driver);
    }
 
    @Test
    public void verifyCheckoutPage() {
        driver.get("https://mycodeyatra.com/checkout");
 
        // Perform some functional actions
        driver.findElement(By.id("cart-btn")).click();
 
        // Take a visual snapshot!
        // We can pass an array of widths to instantly render this DOM on mobile, tablet, and desktop
        percy.snapshot("Checkout Modal View", Arrays.asList(375, 768, 1440));
    }
 
    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}