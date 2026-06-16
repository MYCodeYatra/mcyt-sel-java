package com.mycodeyatra.accessibility;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
 
public class AriaValidationTest {
 
    private WebDriver driver;
 
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
    }
 
    @Test
    public void verifyAccordionAriaState() {
        driver.get("https://mycodeyatra.com/faq");
 
        WebElement accordionButton = driver.findElement(By.id("faq-question-1"));
 
        // 1. Verify initial state is collapsed
        String initialState = accordionButton.getAttribute("aria-expanded");
        Assert.assertEquals(initialState, "false", "Accordion should be collapsed initially!");
 
        // 2. Click the accordion to open it
        accordionButton.click();
 
        // 3. Verify the ARIA state updated to true
        String expandedState = accordionButton.getAttribute("aria-expanded");
        Assert.assertEquals(expandedState, "true", "Screen Reader was not notified of expansion!");
 
        // 4. Verify the hidden content is now visible
        WebElement answerText = driver.findElement(By.id("faq-answer-1"));
        Assert.assertTrue(answerText.isDisplayed(), "Answer text is not visible!");
    }
 
    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}