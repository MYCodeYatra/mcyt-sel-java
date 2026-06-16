package com.mycodeyatra.security;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.ClientApi;
 
import java.io.File;
import java.nio.file.Files;
 
public class ZapSecurityIntegrationTest {
 
    private WebDriver driver;
 
    // Connect to ZAP running on localhost
    private ClientApi zapApi = new ClientApi("localhost", 8080, "YOUR_ZAP_API_KEY");
    private final String TARGET_URL = "https://mycodeyatra.com";
 
    @BeforeClass
    public void setup() {
        driver = ZapProxyManager.getProxiedDriver();
    }
 
    @Test
    public void executeFunctionalTestAndRecordTraffic() throws Exception {
        // 1. Functional execution - ZAP is passively recording this!
        driver.get(TARGET_URL + "/login");
        driver.findElement(By.id("username")).sendKeys("testuser");
        driver.findElement(By.id("password")).sendKeys("testpass");
        driver.findElement(By.id("loginBtn")).click();
 
        // Ensure page loads
        Thread.sleep(3000);
 
        // 2. Trigger ZAP Active Scan via API
        System.out.println("Spidering complete. Starting Active Scan...");
        zapApi.ascan.scan(TARGET_URL, "True", "False", null, null, null);
 
        // Polling to wait for scan to finish
        int progress;
        do {
            Thread.sleep(5000);
            progress = Integer.parseInt(zapApi.ascan.status(null).toString());
            System.out.println("Active Scan Progress: " + progress + "%");
        } while (progress < 100);
 
        System.out.println("Security Scan Complete!");
    }
 
    @AfterClass
    public void teardownAndGenerateReport() throws Exception {
        if (driver != null) {
            driver.quit();
        }
 
        // Retrieve HTML Report from ZAP API
        byte[] reportBytes = zapApi.core.htmlreport();
        File reportFile = new File("target/ZAP_Security_Report.html");
        Files.write(reportFile.toPath(), reportBytes);
 
        System.out.println("Vulnerability Report saved to: " + reportFile.getAbsolutePath());
    }
}