package com.mycodeyatra.tests;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
public class JsonSchemaTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.basePath = "/api";
    }
    @Test
    public void testJsonSchemaValidation() {
        System.out.println("\\n--- Executing JSON Schema Validation Test ---");
        given()
            .when()
            .get("/health") 
            .then()
            .statusCode(200)
            // Validating the entire JSON structure perfectly matches schema.json
            .body(matchesJsonSchemaInClasspath("schema.json"));
        System.out.println("JSON Schema validation passed successfully!");
    }
}