package com.mycodeyatra.visual;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
 
import java.util.HashMap;
import java.util.Map;
 
public class MobileEmulationTest {
 
    @Test
    public void testApplicationAsIphoneX() {
        // 1. Create a map containing the emulation settings
        Map<String, String> mobileEmulation = new HashMap<>();
 
        // You can specify exact dimensions, or just use a known device name
        mobileEmulation.put("deviceName", "iPhone X");
 
        // 2. Pass the emulation map into ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
 
        // 3. Launch the browser
        WebDriver driver = new ChromeDriver(options);
 
        // 4. Navigate to the app
        driver.get("https://mycodeyatra.com");
 
        // 5. Verify the User-Agent was successfully spoofed!
        String userAgent = (String) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return navigator.userAgent;");
 
        System.out.println("Current User-Agent: " + userAgent);
        Assert.assertTrue(userAgent.contains("iPhone"), "Backend still thinks we are on Desktop!");
 
        driver.quit();
    }
}