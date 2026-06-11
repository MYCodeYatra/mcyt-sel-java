package com.mycodeyatra.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
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

public class FrameTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testSwitchToIFrameAndClick() {
        String practiceUrl = "https://practice.mycodeyatra.com/#/frames";
        System.out.println("Navigating to Live Practice Site: " + practiceUrl);
        driver.get(practiceUrl);

        // 1. Locate the iframe element
        System.out.println("Locating iframe element...");
        WebElement iframeElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@data-testid='iframe-container']"))
        );

        // 2. Switch context to the iframe
        System.out.println("Switching to iframe context...");
        driver.switchTo().frame(iframeElement);

        // 3. Interact with the button inside the iframe
        System.out.println("Locating and clicking button inside iframe...");
        WebElement iframeBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("iframe-btn"))
        );
        iframeBtn.click();

        // 4. Verify message inside the iframe
        WebElement msgElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("msg"))
        );
        String msgText = msgElement.getText();
        System.out.println("Message inside iframe: " + msgText);
        Assert.assertEquals(msgText, "IFrame Button Clicked!", "Assertion failed inside iframe!");

        // 5. Switch back to the parent page (default content)
        System.out.println("Switching back to default content...");
        driver.switchTo().defaultContent();

        // 6. Verify interaction with default content is possible
        WebElement pageTitle = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//h2"))
        );
        System.out.println("Parent page header text: " + pageTitle.getText());
        Assert.assertTrue(pageTitle.getText().contains("iFrames"), "Failed to switch back to parent context!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}
