package com.mycodeyatra.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

public class AssertionsDemoTest {

    @Test
    public void testHardAssertion() {
        System.out.println("[Assertions] Executing Hard Assertion Test...");
        String pageTitle = "Dashboard";
        Assert.assertEquals(pageTitle, "Dashboard", "Page title does not match!");
        System.out.println("[Assertions] Hard assertion passed successfully.");
    }

    @Test
    public void testSoftAssertion() {
        System.out.println("[Assertions] Executing Soft Assertion Test...");
        SoftAssert softAssert = new SoftAssert();
        
        String username = "JohnDoe";
        String role = "Manager";
        
        softAssert.assertEquals(username, "JohnDoe", "Username mismatch!");
        System.out.println("[Assertions] First soft assertion checked.");
        
        softAssert.assertEquals(role, "Admin", "User role mismatch!"); // Intended failure
        System.out.println("[Assertions] Second soft assertion checked (intended failure).");
        
        System.out.println("[Assertions] Finalizing soft assertions via assertAll...");
        softAssert.assertAll(); // Flunks the test here and reports all failures
    }

    @Test
    public void testAssertJAssertion() {
        System.out.println("[Assertions] Executing AssertJ Fluent Assertion Test...");
        String welcomeMessage = "Welcome, Pankaj Kumar!";
        
        assertThat(welcomeMessage)
            .isNotNull()
            .startsWith("Welcome")
            .contains("Pankaj");
            
        List<String> activeModules = List.of("Settings", "Reports", "Analytics");
        assertThat(activeModules)
            .hasSize(3)
            .contains("Reports")
            .doesNotContain("Admin Panel");
            
        System.out.println("[Assertions] AssertJ fluent assertions passed successfully.");
    }
}
