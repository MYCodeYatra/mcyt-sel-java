package com.mycodeyatra.tests;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
 
public class CollaborativeOrderTest {
 
    private WebDriver customerDriver;
    private WebDriver adminDriver;
 
    @BeforeMethod
    public void setup() {
        // Launch two completely isolated browsers
        customerDriver = new ChromeDriver();
        adminDriver = new ChromeDriver();
    }
 
    @Test
    public void verifyAdminCanApproveCustomerOrder() {
        // 1. Customer logs in and creates an order
        CustomerPortalPage customerPortal = new CustomerPortalPage(customerDriver);
        customerPortal.login("john_doe", "password123");
        String orderId = customerPortal.placeOrder("Laptop");
 
        Assert.assertEquals(customerPortal.getOrderStatus(orderId), "PENDING");
 
        // 2. Admin logs into the backend in their own isolated browser
        AdminDashboardPage adminDashboard = new AdminDashboardPage(adminDriver);
        adminDashboard.login("super_admin", "adminPass!");
        adminDashboard.navigateToOrders();
 
        // Admin approves the specific order created by the customer
        adminDashboard.approveOrder(orderId);
 
        // 3. Customer checks their portal in real-time
        customerDriver.navigate().refresh();
        Assert.assertEquals(customerPortal.getOrderStatus(orderId), "APPROVED", 
                "Customer UI did not reflect Admin approval!");
    }
 
    @AfterMethod
    public void teardown() {
        if (customerDriver != null) customerDriver.quit();
        if (adminDriver != null) adminDriver.quit();
    }
}