package com.mycodeyatra.tests;
 
import com.mycodeyatra.api.ApiClient;
import com.mycodeyatra.pages.StoreFrontPage;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
 
public class HybridProductTest extends BaseTest {
 
    @Test
    public void verifyProductRendersOnStorefront() {
        // 1. Setup Data via API (Lightning Fast)
        String productPayload = "{ \"name\": \"Selenium Masterclass\", \"price\": 99.99 }";
 
        Response apiResponse = ApiClient.getBaseSpec()
                .body(productPayload)
                .post("/v1/products");
 
        Assert.assertEquals(apiResponse.getStatusCode(), 201);
        String productId = apiResponse.jsonPath().getString("id");
 
        // 2. Validate via UI (Selenium)
        StoreFrontPage storePage = new StoreFrontPage(driver);
        storePage.navigateToStore();
        storePage.searchFor("Selenium Masterclass");
 
        Assert.assertTrue(storePage.isProductVisible(productId), 
                "Product created via API was not found on the UI!");
 
        // 3. Teardown via API
        ApiClient.getBaseSpec().delete("/v1/products/" + productId);
    }
}