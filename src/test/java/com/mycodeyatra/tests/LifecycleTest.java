package com.mycodeyatra.tests;
import com.mycodeyatra.driver.DriverFactory;
import com.mycodeyatra.listeners.CustomTestListener;
import com.mycodeyatra.listeners.RetryAnalyzer;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
@Listeners(CustomTestListener.class)
public class LifecycleTest {
 private WebDriver driver;
 @BeforeClass
 public void suiteSetup() {
  System.out.println("[Lifecycle] @BeforeClass  -> Suite-level setup (runs once per class)");
 }
 @BeforeMethod
 @Parameters("browser")
 public void setup(@Optional("chrome") String browser) {
  System.out.println("[Lifecycle] @BeforeMethod -> Initialising " + browser + " browser...");
  driver = DriverFactory.initDriver(browser);
  driver.manage().window().maximize();
 }
 @Test(description = "Verify the practice site title loads correctly")
 public void verifyPageTitle() {
  System.out.println("[Lifecycle] @Test         -> verifyPageTitle running...");
  driver.get("https://practice.mycodeyatra.com/");
  String title = driver.getTitle();
  System.out.println("[Lifecycle] Page Title: " + title);
  Assert.assertFalse(title.isEmpty(), "Page title should not be empty");
 }
 @Test(description = "Simulate a flaky test with RetryAnalyzer",
  retryAnalyzer = RetryAnalyzer.class)
 public void simulateFlakyTest() {
  System.out.println("[Lifecycle] @Test         -> simulateFlakyTest running...");
  driver.get("https://practice.mycodeyatra.com/");
  String url = driver.getCurrentUrl();
  Assert.assertTrue(url.contains("practice.mycodeyatra.com"),
   "URL should contain practice.mycodeyatra.com");
 }
 @AfterMethod
 public void teardown() {
  System.out.println("[Lifecycle] @AfterMethod  -> Closing browser session...");
  DriverFactory.quitDriver();
 }
}