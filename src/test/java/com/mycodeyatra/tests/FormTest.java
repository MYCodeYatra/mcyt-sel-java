package com.mycodeyatra.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class FormTest {
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
    public void testFormSubmissionFlow() {
        System.out.println("Navigating to: https://practice.mycodeyatra.com/");
        driver.get("https://practice.mycodeyatra.com/");

        System.out.println("Opening Sandbox Arena...");
        WebElement sandboxLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Sandbox Arena')]")));
        sandboxLink.click();

        System.out.println("Opening Form Practice Page...");
        WebElement formTile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[text()='Form Practice']")));
        formTile.click();

        // 1. Text Inputs
        System.out.println("Filling text inputs...");
        driver.findElement(By.xpath("//input[@data-testid='full-name']")).sendKeys("Jane Doe");
        driver.findElement(By.xpath("//input[@data-testid='email']")).sendKeys("jane.doe@example.com");
        driver.findElement(By.xpath("//input[@data-testid='phone']")).sendKeys("1234567890");

        // 2. Radio Buttons (Gender)
        System.out.println("Selecting radio button...");
        driver.findElement(By.xpath("//input[@data-testid='gender-female']")).click();

        // 3. Checkboxes (Interests)
        System.out.println("Selecting interest checkboxes...");
        driver.findElement(By.xpath("//input[@data-testid='interest-automation']")).click();
        driver.findElement(By.xpath("//input[@data-testid='interest-testing']")).click();

        // 4. Select Single Dropdown (Country)
        System.out.println("Selecting country from single select dropdown...");
        WebElement countryDropdown = driver.findElement(By.xpath("//select[@data-testid='country-select']"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText("India");

        // 5. Select Multi-Dropdown (Automation Tools)
        System.out.println("Selecting tools from multi-select dropdown...");
        WebElement toolsDropdown = driver.findElement(By.xpath("//select[@data-testid='tools-multi-select']"));
        Select toolsSelect = new Select(toolsDropdown);
        toolsSelect.selectByValue("Selenium");
        toolsSelect.selectByValue("Playwright");

        // 6. Textarea (Bio)
        System.out.println("Writing bio...");
        driver.findElement(By.xpath("//textarea[@data-testid='bio']")).sendKeys("Senior QA Engineer specializing in automated frameworks.");

        // 7. Submit Form
        System.out.println("Submitting form...");
        driver.findElement(By.xpath("//button[@data-testid='submit-btn']")).click();

        // --- VALIDATIONS ---
        System.out.println("Validating success alert message...");
        WebElement successMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid='success-msg']"))
        );
        Assert.assertEquals(successMsg.getText(), "Form submitted successfully!", "Form failed submission validation.");

        System.out.println("Validating results summary values...");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-name']")).getText(), "Jane Doe");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-email']")).getText(), "jane.doe@example.com");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-gender']")).getText(), "Female");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-country']")).getText(), "India");
        Assert.assertTrue(driver.findElement(By.xpath("//span[@data-testid='result-tools']")).getText().contains("Selenium"));
        Assert.assertTrue(driver.findElement(By.xpath("//span[@data-testid='result-tools']")).getText().contains("Playwright"));

        System.out.println("Form handling test validations complete!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}
