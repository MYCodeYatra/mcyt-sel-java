package com.mycodeyatra.tests;
 
import com.mycodeyatra.utils.MfaUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
 
public class MfaLoginTest extends BaseTest {
 
    // Store this in your .env or secure vault!
    private static final String MFA_SECRET = "JBSWY3DPEHPK3PXP"; 
 
    @Test
    public void verifyMfaLoginFlow() {
 
        // 1. Enter standard credentials
        driver.get("https://mycodeyatra.com/login");
        driver.findElement(By.id("username")).sendKeys("mfa_user@mycompany.com");
        driver.findElement(By.id("password")).sendKeys("SecurePass123!");
        driver.findElement(By.id("loginBtn")).click();
 
        // 2. Wait for the MFA challenge screen
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mfaCodeInput")));
 
        // 3. Generate the TOTP code dynamically in Java
        String generatedCode = MfaUtility.getTwoFactorCode(MFA_SECRET);
        System.out.println("Injecting MFA Code: " + generatedCode);
 
        // 4. Inject the code and submit
        driver.findElement(By.id("mfaCodeInput")).sendKeys(generatedCode);
        driver.findElement(By.id("submitMfaBtn")).click();
 
        // 5. Verify successful authentication
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        Assert.assertTrue(driver.getTitle().contains("Dashboard"));
    }
}