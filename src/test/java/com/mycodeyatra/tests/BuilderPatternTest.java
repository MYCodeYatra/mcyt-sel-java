package com.mycodeyatra.tests;
import com.mycodeyatra.driver.DriverFactory;
import com.mycodeyatra.models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.Duration;
public class BuilderPatternTest {
    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        // Utilizing our thread-safe DriverFactory
        driver = DriverFactory.initDriver(browser);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void testFormSubmissionWithBuilderPattern() {
        // Constructing the test data using the Builder Design Pattern
        User testUser = new User.Builder()
                .setFullName("David Builder")
                .setEmail("david.builder@example.com")
                .setPhone("9876543210")
                .setGender("Male")
                .setCountry("India")
                .setTool("Selenium")
                .setBio("Automating form submissions using Builder Pattern payload constructions.")
                .build();
        String targetUrl = "https://practice.mycodeyatra.com/#/form-practice";
        System.out.println("[BuilderPatternTest] Navigating to: " + targetUrl);
        driver.get(targetUrl);
        System.out.println("[BuilderPatternTest] Filling form using Builder payload objects...");
        // 1. Fill Text Inputs
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-testid='full-name']"))).sendKeys(testUser.getFullName());
        driver.findElement(By.xpath("//input[@data-testid='email']")).sendKeys(testUser.getEmail());
        driver.findElement(By.xpath("//input[@data-testid='phone']")).sendKeys(testUser.getPhone());
        // 2. Select Radio Button dynamically
        String genderTestId = "gender-" + testUser.getGender().toLowerCase();
        driver.findElement(By.xpath("//input[@data-testid='" + genderTestId + "']")).click();
        // 3. Select Required Interest Checkbox
        driver.findElement(By.xpath("//input[@data-testid='interest-automation']")).click();
        // 4. Select Country Dropdown
        WebElement countryDropdown = driver.findElement(By.xpath("//select[@data-testid='country-select']"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText(testUser.getCountry());
        // 5. Select Multi-Dropdown Tool
        WebElement toolsDropdown = driver.findElement(By.xpath("//select[@data-testid='tools-multi-select']"));
        Select toolsSelect = new Select(toolsDropdown);
        toolsSelect.deselectAll();
        toolsSelect.selectByValue(testUser.getTool());
        // 6. Fill Bio
        driver.findElement(By.xpath("//textarea[@data-testid='bio']")).sendKeys(testUser.getBio());
        // 7. Submit form
        System.out.println("[BuilderPatternTest] Submitting form...");
        driver.findElement(By.xpath("//button[@data-testid='submit-btn']")).click();
        // 8. Assertions
        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid='success-msg']"))
        );
        Assert.assertEquals(successMsg.getText(), "Form submitted successfully!");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-name']")).getText(), testUser.getFullName());
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-email']")).getText(), testUser.getEmail());
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-gender']")).getText(), testUser.getGender());
        Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid='result-country']")).getText(), testUser.getCountry());
        System.out.println("[BuilderPatternTest] Completed form submission validation successfully!");
    }
    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}