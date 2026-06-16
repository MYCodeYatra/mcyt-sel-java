package com.mycodeyatra.tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
public class ScreenshotTest {
    private WebDriver driver;
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new"); // Capture cleanly in headless CI runners
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @Test
    public void testScreenshotCapture() throws IOException {
        System.out.println("Navigating to: https://practice.mycodeyatra.com/");
        driver.get("https://practice.mycodeyatra.com/");
        // Create target screenshot folder inside target directory
        File screenshotDir = new File("target/screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
        // 1. Capture Full Page Screenshot
        System.out.println("Capturing full page screenshot...");
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File("target/screenshots/full_page_screenshot.png");
        FileHandler.copy(srcFile, destFile);
        System.out.println("Full page screenshot saved to: " + destFile.getAbsolutePath());
        Assert.assertTrue(destFile.exists(), "Full page screenshot file was not created.");
        // 2. Capture Element Specific Screenshot (Selenium 4 Feature)
        System.out.println("Locating sandbox header element...");
        WebElement headerElement = driver.findElement(By.xpath("//h1"));
        System.out.println("Capturing specific element screenshot...");
        File elementSrcFile = headerElement.getScreenshotAs(OutputType.FILE);
        File elementDestFile = new File("target/screenshots/element_header_screenshot.png");
        FileHandler.copy(elementSrcFile, elementDestFile);
        System.out.println("Element screenshot saved to: " + elementDestFile.getAbsolutePath());
        Assert.assertTrue(elementDestFile.exists(), "Element screenshot file was not created.");
        System.out.println("Screenshot capture tests executed successfully!");
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}