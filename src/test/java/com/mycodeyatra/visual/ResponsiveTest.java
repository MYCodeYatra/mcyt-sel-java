package com.mycodeyatra.visual;
 
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
 
public class ResponsiveTest {
 
    private WebDriver driver;
 
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://mycodeyatra.com");
    }
 
    @Test
    public void testHamburgerMenuAppearsOnMobile() throws InterruptedException {
        // 1. Set to Desktop (1920x1080)
        driver.manage().window().setSize(new Dimension(1920, 1080));
        Thread.sleep(1000); // Wait for CSS transitions
 
        WebElement hamburgerMenu = driver.findElement(By.id("mobile-nav-toggle"));
        Assert.assertFalse(hamburgerMenu.isDisplayed(), "Hamburger menu should NOT be visible on Desktop!");
 
        // 2. Set to Mobile (375x667)
        driver.manage().window().setSize(new Dimension(375, 667));
        Thread.sleep(1000); // Wait for CSS transitions
 
        Assert.assertTrue(hamburgerMenu.isDisplayed(), "Hamburger menu MUST be visible on Mobile!");
    }
 
    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}