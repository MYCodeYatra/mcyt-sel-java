package com.mycodeyatra.visual;
 
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
 
public class CrossDeviceVisualTest {
 
    @Test
    public void runUltrafastGrid() {
        WebDriver driver = new ChromeDriver();
 
        // 1. Initialize the Visual Grid Runner (Cloud Execution)
        VisualGridRunner runner = new VisualGridRunner(10); // 10 parallel threads
        Eyes eyes = new Eyes(runner);
 
        // 2. Configure multiple viewports
        Configuration config = new Configuration();
        config.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
 
        // Add Desktop Browsers
        config.addBrowser(1920, 1080, BrowserType.CHROME);
        config.addBrowser(1440, 900, BrowserType.FIREFOX);
 
        // Add Mobile Emulation
        config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
        config.addDeviceEmulation(DeviceName.iPad, ScreenOrientation.LANDSCAPE);
 
        eyes.setConfiguration(config);
 
        // 3. Run the test
        driver.get("https://mycodeyatra.com/dashboard");
 
        eyes.open(driver, "MyCodeYatra App", "Responsive Dashboard Test");
        eyes.checkWindow("Dashboard Main View");
        eyes.closeAsync(); // Fire and forget to the cloud
 
        driver.quit();
 
        // 4. Wait for all 4 cloud environments to finish analyzing
        runner.getAllTestResults();
    }
}