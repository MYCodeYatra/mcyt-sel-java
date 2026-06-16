package com.mycodeyatra.api.filters;
 
import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import io.restassured.filter.Filter;
 
public class ContractValidator {
 
    private static final String SWAGGER_URL = "https://api.mycodeyatra.com/v2/api-docs";
 
    /**
     * Returns a RestAssured Filter that automatically validates 
     * requests and responses against the OpenAPI specification.
     */
    public static Filter getOpenApiFilter() {
        return new OpenApiValidationFilter(SWAGGER_URL);
    }
}