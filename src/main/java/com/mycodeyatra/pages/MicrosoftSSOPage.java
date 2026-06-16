package com.mycodeyatra.pages;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
 
public class MicrosoftSSOPage {
 
    private WebDriver driver;
    private WebDriverWait wait;
 
    // Microsoft Login Locators
    private By emailInput = By.name("loginfmt");
    private By nextButton = By.id("idSIButton9");
    private By passwordInput = By.name("passwd");
    private By signInButton = By.id("idSIButton9"); // Reused ID by Microsoft
    private By staySignedInNoBtn = By.id("idBtn_Back");
 
    public MicrosoftSSOPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
 
    public void loginToMicrosoft(String email, String password) {
        // 1. Enter Email
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
        driver.findElement(nextButton).click();
 
        // 2. Enter Password (Crucial to wait for it to become visible)
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
 
        // Wait for the button text to change or become clickable
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
 
        // 3. Handle the "Stay signed in?" prompt
        try {
            wait.until(ExpectedConditions.elementToBeClickable(staySignedInNoBtn)).click();
        } catch (Exception e) {
            // Sometimes Microsoft skips this screen depending on tenant settings
            System.out.println("Stay Signed In prompt did not appear, continuing...");
        }
    }
}