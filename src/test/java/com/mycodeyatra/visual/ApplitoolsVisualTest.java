package com.mycodeyatra.visual;
 
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.MatchLevel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
 
public class ApplitoolsVisualTest {
 
    private WebDriver driver;
    private Eyes eyes;
 
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
 
        // 1. Initialize the Eyes SDK
        eyes = new Eyes();
 
        // 2. Set your API Key (Always use environment variables!)
        Configuration config = new Configuration();
        config.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
 
        // 3. Set the Match Level
        // STRICT = Human eye comparison
        // LAYOUT = Ignores text/colors, checks structure only (Great for dynamic data!)
        config.setMatchLevel(MatchLevel.STRICT);
 
        eyes.setConfiguration(config);
    }
 
    @Test
    public void verifyLoginDashboardLayout() {
        // 4. Open Applitools session
        eyes.open(driver, "MyCodeYatra App", "Dashboard Visual Test");
 
        driver.get("https://mycodeyatra.com/dashboard");
 
        // 5. Tell Applitools to take a snapshot and analyze it using AI
        eyes.checkWindow("Main Dashboard");
 
        // 6. Close the session (This automatically asserts if visual differences exist)
        eyes.close();
    }
 
    @AfterMethod
    public void teardown() {
        driver.quit();
        eyes.abortIfNotClosed(); // Failsafe
    }
}