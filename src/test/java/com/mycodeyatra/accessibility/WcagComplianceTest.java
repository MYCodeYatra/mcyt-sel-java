package com.mycodeyatra.accessibility;
 
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
 
import java.util.List;
 
public class WcagComplianceTest {
 
    private WebDriver driver;
 
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
    }
 
    @Test
    public void verifyDashboardAccessibility() {
        // 1. Navigate to the page you want to test
        driver.get("https://mycodeyatra.com/dashboard");
 
        // 2. Initialize the AxeBuilder and run the scan
        AxeBuilder builder = new AxeBuilder();
        Results scanResults = builder.analyze(driver);
 
        // 3. Extract the list of violations
        List<Rule> violations = scanResults.getViolations();
 
        // 4. Print detailed error messages if violations exist
        if (!violations.isEmpty()) {
            System.out.println("Found " + violations.size() + " Accessibility Violations:");
 
            for (Rule violation : violations) {
                System.out.println("-------------------------------------------------");
                System.out.println("Rule ID: " + violation.getId());
                System.out.println("Impact: " + violation.getImpact());
                System.out.println("Description: " + violation.getDescription());
                System.out.println("Help URL: " + violation.getHelpUrl());
            }
        }
 
        // 5. Fail the test if there is even a single violation
        Assert.assertEquals(violations.size(), 0, "WCAG AA Violations found on Dashboard!");
    }
 
    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}