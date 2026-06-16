package com.mycodeyatra.security;
 
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
 
import java.util.Arrays;
import java.util.List;
 
public class CspValidationTest {
 
    @Test
    public void verifyCspIsStrict() {
        // Hit the application URL
        Response response = RestAssured.get("https://mycodeyatra.com");
 
        // 1. Extract the CSP Header
        String cspHeader = response.header("Content-Security-Policy");
        Assert.assertNotNull(cspHeader, "CRITICAL: No CSP Header found!");
 
        System.out.println("Current CSP: " + cspHeader);
 
        // 2. Split the directives by semicolon
        List<String> directives = Arrays.asList(cspHeader.split(";"));
 
        // 3. Analyze each directive for dangerous flags
        for (String directive : directives) {
            String cleanDirective = directive.trim().toLowerCase();
 
            // Fail the build if unsafe-inline is found in script-src
            if (cleanDirective.startsWith("script-src")) {
                Assert.assertFalse(cleanDirective.contains("'unsafe-inline'"), 
                    "SECURITY VULNERABILITY: script-src contains 'unsafe-inline'. XSS protection is compromised!");
 
                Assert.assertFalse(cleanDirective.contains("'unsafe-eval'"), 
                    "SECURITY VULNERABILITY: script-src contains 'unsafe-eval'!");
            }
 
            // Similarly check object-src to prevent Flash/Java applet injection
            if (cleanDirective.startsWith("object-src")) {
                Assert.assertTrue(cleanDirective.contains("'none'"), 
                    "object-src must be set to 'none' to prevent plugin exploitation.");
            }
        }
    }
}