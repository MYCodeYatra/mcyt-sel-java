package com.mycodeyatra.tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.Duration;
public class CrossBrowserTest {
    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        System.out.println("Initializing browser session: " + browser);
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");
            driver = new FirefoxDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
            driver = new EdgeDriver(options);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void testPageNavigationAndTitle() {
        String practiceUrl = "https://practice.mycodeyatra.com/";
        System.out.println("Navigating to: " + practiceUrl);
        driver.get(practiceUrl);
        // Verify page loads and displays correct branding title
        String pageTitle = driver.getTitle();
        System.out.println("Page Title Captured: " + pageTitle);
        Assert.assertTrue(pageTitle.contains("MyCodeYatra"), "Branding title validation failed!");
        // Open Sandbox tile
        WebElement sandboxLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Sandbox Arena')]")));
        sandboxLink.click();
        // Wait for heading text to transit and match
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h2"), "Test Practice Sandbox"));
        WebElement sandboxHeader = driver.findElement(By.xpath("//h2"));
        System.out.println("Header inside Sandbox Arena: " + sandboxHeader.getText());
        Assert.assertTrue(sandboxHeader.getText().contains("Sandbox"), "Failed to load Sandbox Arena!");
        System.out.println("Cross browser test passed successfully!");
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}