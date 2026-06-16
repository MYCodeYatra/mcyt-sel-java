package com.mycodeyatra.tests;
 
import com.mycodeyatra.api.ApiClient;
import com.mycodeyatra.pages.CheckoutPage;
import org.testng.Assert;
import org.testng.annotations.Test;
 
public class HybridCheckoutTest extends BaseTest {
 
    @Test
    public void verifyUiCheckoutUpdatesBackend() {
        // 1. Perform complex UI action
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.navigateToCart();
        String orderId = checkout.completePurchase("Visa", "1234-5678-9012-3456");
 
        // 2. Instantly verify backend state via API
        String orderStatus = ApiClient.getBaseSpec()
                .get("/v1/orders/" + orderId)
                .jsonPath().getString("status");
 
        Assert.assertEquals(orderStatus, "PAID", 
                "Backend order status did not update after UI checkout!");
    }
}