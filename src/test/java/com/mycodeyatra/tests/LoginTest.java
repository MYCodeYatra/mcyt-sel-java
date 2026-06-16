package com.mycodeyatra.tests;
 
import com.mycodeyatra.pages.LoginPage;
import com.mycodeyatra.pages.DashboardPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.Test;
 
@SpringBootTest(classes = com.mycodeyatra.config.TestApplication.class)
public class LoginTest extends SpringBaseTest {
 
    @Autowired
    private LoginPage loginPage;
 
    @Autowired
    private DashboardPage dashboardPage;
 
    @Test
    public void testSuccessfulLoginInContainer() {
        driver.get("https://practice.mycodeyatra.com/login");
 
        loginPage.login("admin", "admin123");
 
        Assert.assertTrue(dashboardPage.isDisplayed(), "Dashboard should be visible.");
    }
}