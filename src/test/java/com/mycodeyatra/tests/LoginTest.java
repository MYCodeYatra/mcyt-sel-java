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

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    public void testLoginScenario() {
        // 1. Navigate to the Live Practice Site
        System.out.println("Navigating to: https://practice.mycodeyatra.com/");
        driver.get("https://practice.mycodeyatra.com/");

        // 2. Open Sandbox Arena Page
        System.out.println("Navigating to Sandbox Arena...");
        WebElement sandboxLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Sandbox Arena')]")));
        sandboxLink.click();

        // 3. Click the Authentication & Session Card Tile
        System.out.println("Clicking Authentication & Session Tile...");
        WebElement loginTile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[text()='Authentication & Session']")));
        loginTile.click();

        // 4. Validate Sign In Header
        WebElement signInHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Sign In']")));
        Assert.assertNotNull(signInHeader, "Sign In page failed to load.");

        // 5. Test Invalid Login
        System.out.println("Testing Invalid Login Credentials...");
        WebElement usernameInput = driver.findElement(By.xpath("//input[@data-testid='username']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@data-testid='password']"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@data-testid='login-btn']"));

        usernameInput.clear();
        usernameInput.sendKeys("invalid_user");
        passwordInput.clear();
        passwordInput.sendKeys("wrong_pass");
        loginButton.click();

        // Verify invalid error alert message
        WebElement errorAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid='login-error']")));
        String errorText = errorAlert.getText();
        System.out.println("Error Alert Received: " + errorText);
        Assert.assertTrue(errorText.contains("Invalid username or password"), "Incorrect error validation message.");

        // 6. Test Valid Login
        System.out.println("Testing Valid Login Credentials...");
        // Re-locate input fields due to potential DOM refresh
        usernameInput = driver.findElement(By.xpath("//input[@data-testid='username']"));
        passwordInput = driver.findElement(By.xpath("//input[@data-testid='password']"));
        loginButton = driver.findElement(By.xpath("//button[@data-testid='login-btn']"));

        usernameInput.clear();
        usernameInput.sendKeys("admin");
        passwordInput.clear();
        passwordInput.sendKeys("admin123");
        loginButton.click();

        // Verify profile title welcome message
        WebElement profileWelcome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@data-testid='profile-title']")));
        String welcomeMsg = profileWelcome.getText();
        System.out.println("Profile Page Header: " + welcomeMsg);
        Assert.assertTrue(welcomeMsg.contains("Welcome back, admin!"), "Failed to login or load profile page.");

        // Clean logout
        WebElement logoutButton = driver.findElement(By.xpath("//button[@data-testid='logout-btn']"));
        logoutButton.click();
        
        // Wait back to Sign In header
        signInHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Sign In']")));
        Assert.assertNotNull(signInHeader, "Failed to log out safely.");
        System.out.println("Logout complete!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}
