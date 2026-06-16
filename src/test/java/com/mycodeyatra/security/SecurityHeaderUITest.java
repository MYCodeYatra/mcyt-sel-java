package com.mycodeyatra.security;
 
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
 
import java.util.Optional;
 
public class SecurityHeaderUITest {
 
    private ChromeDriver driver;
 
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
    }
 
    @Test
    public void verifyHeadersDuringNavigation() {
        // 1. Get the DevTools session
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
 
        // 2. Enable Network tracking
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
 
        // 3. Add a listener to intercept all responses
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            String url = responseReceived.getResponse().getUrl();
 
            // Only check headers for our main application domain, ignore 3rd party scripts
            if (url.equals("https://mycodeyatra.com/")) {
                var headers = responseReceived.getResponse().getHeaders();
 
                System.out.println("Validating Headers for: " + url);
                Assert.assertTrue(headers.containsKey("Strict-Transport-Security"), "Missing HSTS");
                Assert.assertEquals(headers.get("x-frame-options").toString().toUpperCase(), "DENY");
            }
        });
 
        // 4. Navigate (The listener will instantly catch the response)
        driver.get("https://mycodeyatra.com");
    }
 
    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}