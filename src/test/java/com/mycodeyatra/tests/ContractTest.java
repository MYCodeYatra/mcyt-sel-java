package com.mycodeyatra.tests;
 
import com.mycodeyatra.api.ApiClient;
import org.testng.annotations.Test;
 
public class ContractTest {
 
    @Test
    public void verifyUserEndpointContract() {
 
        // The OpenApiValidationFilter will automatically throw an exception 
        // if the response payload or headers violate the swagger.yaml definition.
        ApiClient.getBaseSpec()
                .get("/v1/users/123")
                .then()
                .statusCode(200);
    }
}