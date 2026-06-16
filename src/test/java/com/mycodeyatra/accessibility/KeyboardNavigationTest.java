package com.mycodeyatra.accessibility;
 
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
 
public class KeyboardNavigationTest {
 
    private WebDriver driver;
 
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
    }
 
    @Test
    public void verifyLoginFormKeyboardOperability() {
        driver.get("https://mycodeyatra.com/login");
 
        Actions actions = new Actions(driver);
 
        // 1. Find the first element on the page (usually a "Skip to Content" link or the logo)
        WebElement logo = driver.findElement(By.id("navbar-logo"));
        logo.click(); // Click once just to set the initial focus on the page
 
        // 2. Press TAB to move to the Username field
        actions.sendKeys(Keys.TAB).perform();
        WebElement activeElement = driver.switchTo().activeElement();
        Assert.assertEquals(activeElement.getAttribute("id"), "username", "Focus did not land on Username field!");
 
        // 3. Type the username without calling username.sendKeys()
        actions.sendKeys("adminUser").perform();
 
        // 4. Press TAB to move to the Password field
        actions.sendKeys(Keys.TAB).perform();
        activeElement = driver.switchTo().activeElement();
        Assert.assertEquals(activeElement.getAttribute("id"), "password", "Focus did not land on Password field!");
 
        // 5. Type password
        actions.sendKeys("SecureP@ss123").perform();
 
        // 6. Press TAB to move to the "Remember Me" Checkbox
        actions.sendKeys(Keys.TAB).perform();
        activeElement = driver.switchTo().activeElement();
        Assert.assertEquals(activeElement.getAttribute("type"), "checkbox", "Focus did not land on Checkbox!");
 
        // 7. Toggle checkbox using the SPACEBAR
        actions.sendKeys(Keys.SPACE).perform();
        Assert.assertTrue(activeElement.isSelected(), "Spacebar failed to check the box!");
 
        // 8. Press TAB to the Login Button and hit ENTER
        actions.sendKeys(Keys.TAB).perform();
        actions.sendKeys(Keys.ENTER).perform();
 
        // 9. Verify the login was successful
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Keyboard Login failed!");
    }
 
    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}