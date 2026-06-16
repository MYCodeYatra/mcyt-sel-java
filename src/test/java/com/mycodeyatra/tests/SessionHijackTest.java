package com.mycodeyatra.tests;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
 
public class SessionHijackTest {
 
    @Test
    public void attachToExistingBrowser() {
 
        ChromeOptions options = new ChromeOptions();
 
        // Tell Selenium to connect to the browser we opened manually
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
 
        // Initialize driver with these options
        WebDriver driver = new ChromeDriver(options);
 
        // Selenium will NOT open a new window. It will take control of the existing one!
        System.out.println("Hijacked Page Title: " + driver.getTitle());
 
        // Now you can test just the step you are debugging
        // driver.findElement(By.id("step19-button")).click();
    }
}