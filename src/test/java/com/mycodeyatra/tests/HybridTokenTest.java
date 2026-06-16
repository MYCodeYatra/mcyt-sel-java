package com.mycodeyatra.tests;
 
import com.mycodeyatra.utils.WebStorageUtils;
import io.restassured.RestAssured;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
 
public class HybridTokenTest extends BaseTest {
 
    @Test
    public void createOrderViaApiAfterUiLogin() {
 
        // 1. Standard UI Login
        driver.get("https://mycodeyatra.com/login");
        driver.findElement(By.id("username")).sendKeys("hybrid_user");
        driver.findElement(By.id("password")).sendKeys("Pass123!");
        driver.findElement(By.id("loginBtn")).click();
 
        // Wait for dashboard to load (ensures token is stored)
        wait.until(ExpectedConditions.urlContains("/dashboard"));
 
        // 2. Extract the JWT from Local Storage
        WebStorageUtils storageUtils = new WebStorageUtils(driver);
        String jwtToken = storageUtils.getLocalStorageItem("access_token");
 
        System.out.println("Extracted Token: " + jwtToken);
 
        // 3. Pass Token to RestAssured to create an order instantly
        String apiEndpoint = "https://api.mycodeyatra.com/v1/orders";
        String orderPayload = "{ \"item\": \"Laptop\", \"qty\": 1 }";
 
        RestAssured.given()
            .header("Authorization", "Bearer " + jwtToken)
            .header("Content-Type", "application/json")
            .body(orderPayload)
        .when()
            .post(apiEndpoint)
        .then()
            .statusCode(201); // Created
 
        // 4. Refresh the UI and verify the order is visible
        driver.navigate().refresh();
        boolean isOrderVisible = driver.findElement(By.xpath("//td[text()='Laptop']")).isDisplayed();
 
        Assert.assertTrue(isOrderVisible, "The API-created order did not appear in the UI!");
    }
}