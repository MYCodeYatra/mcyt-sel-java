package com.mycodeyatra.pages;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
 
@Component
public class LoginPage {
 
    private final WebDriver driver;
 
    @FindBy(id = "username")
    private WebElement usernameInput;
 
    @FindBy(id = "password")
    private WebElement passwordInput;
 
    @FindBy(id = "loginBtn")
    private WebElement loginButton;
 
    // Spring injects the WebDriver automatically
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
 
    public void login(String user, String pass) {
        usernameInput.sendKeys(user);
        passwordInput.sendKeys(pass);
        loginButton.click();
    }
}