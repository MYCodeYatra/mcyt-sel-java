package com.mycodeyatra.tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class FirstRestAssuredTest {
    @BeforeClass
    public void setup() {
        // Set the global base URL for all RestAssured requests
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.basePath = "/api";
    }
    @Test
    public void testGetUsers() {
        System.out.println("--- Executing GET /api/users ---");
        // Construct and execute the request
        Response response = RestAssured.given()
                .when()
                .get("/users")
                .then()
                .extract().response();
        // Print the result
        System.out.println("Response Status Code: " + response.getStatusCode());
        // Validate the response using TestNG Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertTrue(response.getBody().asString().contains("data"), "Response body does not contain 'data' array");
        System.out.println("Successfully validated GET /users response!");
    }
}