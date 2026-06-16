package com.mycodeyatra.accessibility;
 
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.deque.html.axecore.results.Results;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
 
public class JsonReportTest {
 
    private WebDriver driver;
 
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
    }
 
    @Test
    public void generateJsonReport() {
        driver.get("https://mycodeyatra.com/dashboard");
 
        // 1. Run the scan
        Results scanResults = new AxeBuilder().analyze(driver);
 
        // 2. Write the raw JSON output to the target directory
        String reportPath = "target/axe-reports/dashboard_a11y";
        AxeReporter.writeResultsToJsonFile(reportPath, scanResults);
 
        System.out.println("JSON Report generated at: " + reportPath + ".json");
    }
 
    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}