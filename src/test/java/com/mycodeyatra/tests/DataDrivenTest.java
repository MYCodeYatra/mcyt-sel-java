package com.mycodeyatra.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import com.mycodeyatra.utils.ExcelUtil;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

public class DataDrivenTest {
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

    @DataProvider(name = "excelUserData")
    public Object[][] getExcelData() {
        String projectPath = System.getProperty("user.dir");
        String excelPath = projectPath + File.separator + "src" + File.separator + "test" 
                + File.separator + "resources" + File.separator + "test_data.xlsx";
        System.out.println("Loading data-driven inputs from Excel path: " + excelPath);
        return ExcelUtil.getSheetData(excelPath, "UserData");
    }

    @Test(dataProvider = "excelUserData")
    public void testFormSubmissionWithMultipleUsers(String name, String email, String phone, 
                                                    String gender, String country, String tool, String bio) {
        String targetUrl = "https://practice.mycodeyatra.com/#/form-practice";
        System.out.println("Executing test iteration for user: " + name + " (" + email + ") on target URL: " + targetUrl);
        driver.get(targetUrl);

        // 1. Fill Text Inputs
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-testid='full-name']"))).sendKeys(name);
        driver.findElement(By.xpath("//input[@data-testid='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@data-testid='phone']")).sendKeys(phone);

        // 2. Select Radio Button for Gender dynamically
        String genderTestId = "gender-" + gender.toLowerCase();
        System.out.println("Selecting gender: " + gender + " using locator data-testid=" + genderTestId);
        driver.findElement(By.xpath("//input[@data-testid='" + genderTestId + "']")).click();

        // 3. Select Interest Checkbox
        driver.findElement(By.xpath("//input[@data-testid='interest-automation']")).click();

        // 4. Select Single Dropdown (Country)
        WebElement countryDropdown = driver.findElement(By.xpath("//select[@data-testid='country-select']"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText(country);

        // 4. Select Multi-Dropdown (Automation Tool)
        WebElement toolsDropdown = driver.findElement(By.xpath("//select[@data-testid='tools-multi-select']"));
        Select toolsSelect = new Select(toolsDropdown);
        toolsSelect.deselectAll();
        toolsSelect.selectByValue(tool);

        // 5. Fill Textarea (Bio)
        driver.findElement(By.xpath("//textarea[@data-testid='bio']")).sendKeys(bio);

        // 6. Submit the Form
        System.out.println("Submitting form...");
        driver.findElement(By.xpath("//button[@data-testid='submit-btn']")).click();

        // 7. Assert success messages and inputs
        WebElement successMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid='success-msg']"))
        );
        Assert.assertEquals(successMsg.getText(), "Form submitted successfully!", "Form submission failed!");
        
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-name']")).getText(), name);
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-email']")).getText(), email);
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-gender']")).getText(), gender);
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-country']")).getText(), country);
        
        System.out.println("Iteration validated successfully for user: " + name);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}
