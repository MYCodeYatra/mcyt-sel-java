package com.mycodeyatra.security;
 
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
 
public class SecurityHeaderApiTest {
 
    @Test
    public void verifySecurityHeadersArePresent() {
        // Hit the front-end URL
        Response response = RestAssured.get("https://mycodeyatra.com");
 
        // Assert HTTP 200 OK
        Assert.assertEquals(response.statusCode(), 200);
 
        // Assert the Big Four Headers
        Assert.assertNotNull(response.header("Strict-Transport-Security"), "HSTS Header Missing!");
        Assert.assertEquals(response.header("X-Frame-Options"), "DENY", "X-Frame-Options should be DENY!");
        Assert.assertEquals(response.header("X-Content-Type-Options"), "nosniff", "MIME Sniffing protection missing!");
        Assert.assertNotNull(response.header("Content-Security-Policy"), "CSP Header Missing!");
    }
}