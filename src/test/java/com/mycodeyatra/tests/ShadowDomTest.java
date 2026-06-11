package com.mycodeyatra.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ShadowDomTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testInteractWithShadowDomNative() {
        String practiceUrl = "https://practice.mycodeyatra.com/#/sandbox";
        System.out.println("Navigating to Live Sandbox Site: " + practiceUrl);
        driver.get(practiceUrl);

        // Click the Shadow DOM practice card/link to navigate to it
        System.out.println("Navigating to Shadow DOM page...");
        driver.get("https://practice.mycodeyatra.com/#/shadow-dom");

        // 1. Locate the shadow host element
        System.out.println("Locating shadow host element...");
        WebElement shadowHost = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("shadow-host"))
        );

        // 2. Fetch the SearchContext representing the Shadow Root (Selenium 4 Feature)
        System.out.println("Accessing Shadow Root natively...");
        SearchContext shadowRoot = shadowHost.getShadowRoot();

        // 3. Locate elements inside the Shadow Root using CSS Selector
        WebElement shadowInput = shadowRoot.findElement(By.cssSelector("#shadow-input"));
        WebElement shadowBtn = shadowRoot.findElement(By.cssSelector("#shadow-btn"));
        WebElement shadowMsg = shadowRoot.findElement(By.cssSelector("#shadow-msg"));

        // 4. Perform actions inside the Shadow DOM
        String textToType = "Hello MyCodeYatra Shadow DOM!";
        System.out.println("Typing text inside Shadow DOM input...");
        shadowInput.sendKeys(textToType);

        System.out.println("Clicking submit button inside Shadow DOM...");
        shadowBtn.click();

        // 5. Verify the results
        String actualMsg = shadowMsg.getText();
        System.out.println("Message displayed inside Shadow DOM: " + actualMsg);
        Assert.assertEquals(actualMsg, "You typed: " + textToType, "Shadow DOM validation failed!");
        System.out.println("Native Shadow DOM test completed successfully!");
    }

    @Test
    public void testInteractWithShadowDomJSExecutor() {
        String practiceUrl = "https://practice.mycodeyatra.com/#/shadow-dom";
        System.out.println("Navigating to Live Shadow DOM URL: " + practiceUrl);
        driver.get(practiceUrl);

        // 1. Locate the shadow host element
        WebElement shadowHost = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("shadow-host"))
        );

        // 2. Locate input and button elements inside the Shadow Root using JavascriptExecutor
        System.out.println("Accessing Shadow Root elements via JavascriptExecutor...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        WebElement shadowInput = (WebElement) js.executeScript(
            "return arguments[0].shadowRoot.querySelector('#shadow-input');", shadowHost
        );
        WebElement shadowBtn = (WebElement) js.executeScript(
            "return arguments[0].shadowRoot.querySelector('#shadow-btn');", shadowHost
        );

        // 3. Interact with elements
        String textToType = "JS Executor Shadow Text";
        System.out.println("Typing text via Selenium using the retrieved JS Element...");
        shadowInput.sendKeys(textToType);
        shadowBtn.click();

        // 4. Validate output message
        WebElement shadowMsg = (WebElement) js.executeScript(
            "return arguments[0].shadowRoot.querySelector('#shadow-msg');", shadowHost
        );
        String actualMsg = shadowMsg.getText();
        System.out.println("Message displayed: " + actualMsg);
        Assert.assertEquals(actualMsg, "You typed: " + textToType, "JS Shadow DOM validation failed!");
        System.out.println("JS Executor Shadow DOM test completed successfully!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}
