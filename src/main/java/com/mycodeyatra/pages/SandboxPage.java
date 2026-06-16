package com.mycodeyatra.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class SandboxPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By header = By.xpath("//h2");
    private By sandboxArenaLink = By.xpath("//span[contains(text(),'Sandbox Arena')]");
    public SandboxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public String getHeaderMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(header)).getText();
    }
    public void clickSandboxArena() {
        wait.until(ExpectedConditions.elementToBeClickable(sandboxArenaLink)).click();
    }
}