package com.mycodeyatra.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class WindowTabTest {
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
    public void testSwitchBetweenWindows() {
        String practiceUrl = "https://practice.mycodeyatra.com/#/frames";
        System.out.println("Navigating to Live Practice Site: " + practiceUrl);
        driver.get(practiceUrl);

        // Get the parent window handle
        String parentWindowHandle = driver.getWindowHandle();
        System.out.println("Parent Window Handle: " + parentWindowHandle);

        // Locate and click the button to open a new tab
        WebElement openTabButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='open-tab-btn']"))
        );
        System.out.println("Clicking button to open child window/tab...");
        openTabButton.click();

        // Wait for the new window to be opened (total 2 windows)
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Get all window handles
        Set<String> allWindowHandles = driver.getWindowHandles();
        System.out.println("Total open windows: " + allWindowHandles.size());

        // Switch to the child window
        boolean switched = false;
        for (String handle : allWindowHandles) {
            if (!handle.equals(parentWindowHandle)) {
                System.out.println("Switching to Child Window Handle: " + handle);
                driver.switchTo().window(handle);
                switched = true;
                break;
            }
        }
        Assert.assertTrue(switched, "Failed to switch to the child tab!");

        // Assert child window title and page content
        String childTitle = driver.getTitle();
        System.out.println("Child Title: " + childTitle);
        Assert.assertEquals(childTitle, "Practice Tab");

        WebElement childHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
        Assert.assertEquals(childHeader.getText(), "Success!");

        // Close the child window
        System.out.println("Closing child window/tab...");
        driver.close();

        // Switch back to parent window
        System.out.println("Switching back to Parent Window...");
        driver.switchTo().window(parentWindowHandle);

        // Assert parent page is active again
        String parentTitle = driver.getTitle();
        System.out.println("Parent Title: " + parentTitle);
        Assert.assertTrue(parentTitle.contains("MyCodeYatra"), "Failed to return to parent page");
    }

    @Test
    public void testSelenium4NewWindowFeature() {
        String practiceUrl = "https://practice.mycodeyatra.com/#/frames";
        System.out.println("Navigating to Live Practice Site: " + practiceUrl);
        driver.get(practiceUrl);

        String parentHandle = driver.getWindowHandle();

        // Selenium 4: Open a new blank tab and switch automatically
        System.out.println("Opening a new blank tab and switching context automatically...");
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://practice.mycodeyatra.com/#/login");

        Assert.assertTrue(driver.getTitle().contains("MyCodeYatra"));
        WebElement loginHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2")));
        Assert.assertTrue(loginHeader.getText().contains("Sign In"), "Failed to load Sign In page in new tab");

        // Close new tab and go back
        driver.close();
        driver.switchTo().window(parentHandle);
        Assert.assertTrue(driver.getTitle().contains("MyCodeYatra"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
    }
}
