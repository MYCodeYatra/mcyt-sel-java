package com.mycodeyatra.api;
 
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
 
public class ApiClient {
 
    public static RequestSpecification getBaseSpec() {
        return RestAssured.given()
                .baseUri("https://api.mycodeyatra.com")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }
}