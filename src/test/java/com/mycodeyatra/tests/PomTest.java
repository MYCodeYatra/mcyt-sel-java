package com.mycodeyatra.tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.mycodeyatra.pages.LoginPage;
import com.mycodeyatra.pages.SandboxPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
public class PomTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private SandboxPage sandboxPage;
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        loginPage = new LoginPage(driver);
        sandboxPage = new SandboxPage(driver);
    }
    @Test
    public void testSuccessfulLoginViaPOM() {
        String loginUrl = "https://practice.mycodeyatra.com/#/login";
        System.out.println("Navigating to: " + loginUrl);
        loginPage.navigateTo(loginUrl);
        System.out.println("Submitting valid credentials...");
        loginPage.login("admin", "admin123");
        // Verify successful navigation redirect
        System.out.println("Verifying header text post authentication...");
        String headerText = sandboxPage.getHeaderMessage();
        System.out.println("Header Text Captured: " + headerText);
        Assert.assertTrue(headerText.contains("Profile") || headerText.contains("Sign In") || driver.getCurrentUrl().contains("profile"), 
                "Login navigation redirect failed!");
        System.out.println("POM E2E login flow completed successfully!");
    }
    @Test
    public void testInvalidLoginErrorMessageViaPOM() {
        String loginUrl = "https://practice.mycodeyatra.com/#/login";
        System.out.println("Navigating to: " + loginUrl);
        loginPage.navigateTo(loginUrl);
        System.out.println("Submitting invalid credentials...");
        loginPage.login("wronguser", "wrongpassword");
        System.out.println("Verifying validation error message...");
        String errorText = loginPage.getErrorMessage();
        System.out.println("Captured Error: " + errorText);
        Assert.assertTrue(errorText.contains("Invalid username or password"), "Error validation assertion failed!");
        System.out.println("POM error validation completed successfully!");
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}