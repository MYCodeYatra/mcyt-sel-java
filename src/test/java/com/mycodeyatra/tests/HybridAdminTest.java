package com.mycodeyatra.tests;
 
import com.mycodeyatra.api.models.UserPayload;
import com.mycodeyatra.api.services.UserService;
import com.mycodeyatra.pages.AdminDashboardPage;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
 
public class HybridAdminTest extends BaseTest {
 
    @Test
    public void verifyAdminCanViewCreatedUser() {
 
        // 1. Generate Data with Builder
        UserPayload newUser = UserPayload.builder()
                .name("Alice")
                .email("alice@automation.com")
                .role("CUSTOMER")
                .build();
 
        // 2. Setup via API Service
        Response apiResponse = UserService.createUser(newUser);
        Assert.assertEquals(apiResponse.getStatusCode(), 201);
        String userId = apiResponse.jsonPath().getString("id");
 
        // 3. Validate via UI
        AdminDashboardPage dashboard = new AdminDashboardPage(driver);
        dashboard.loginAsAdmin();
        dashboard.searchForUser(newUser.getEmail());
 
        Assert.assertTrue(dashboard.isUserPresent(newUser.getName()), 
                "User created via API was not found in Admin UI!");
 
        // 4. Teardown via API Service
        UserService.deleteUser(userId);
    }
}