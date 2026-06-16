package com.mycodeyatra.security;
 
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.testng.Assert;
import org.testng.annotations.Test;
 
import java.util.Date;
 
public class JwtSecurityTest {
 
    @Test
    public void verifyTokenSecurityCompliance() {
 
        // 1. Assume we extracted this token from the UI (as shown in Phase 6)
        String extractedJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI... (Truncated)";
 
        // 2. Parse the token (We use parseClaimsJwt for unsigned parsing just to inspect the payload)
        // Note: To verify the *signature*, you would use parseClaimsJws(token) and provide the public key.
        // For basic structure testing from the client side, we strip the signature temporarily.
        String tokenWithoutSignature = extractedJwt.substring(0, extractedJwt.lastIndexOf('.') + 1);
 
        Claims claims = Jwts.parserBuilder()
                .build()
                .parseClaimsJwt(tokenWithoutSignature)
                .getBody();
 
        System.out.println("Token Subject: " + claims.getSubject());
        System.out.println("Token Expiration: " + claims.getExpiration());
 
        // 3. Security Assertion: Token MUST have an expiration date
        Assert.assertNotNull(claims.getExpiration(), "SECURITY FLAW: Token does not expire!");
 
        // 4. Security Assertion: Token lifespan must be short (e.g., < 60 minutes)
        Date issuedAt = claims.getIssuedAt();
        Date expiresAt = claims.getExpiration();
 
        long lifespanInMillis = expiresAt.getTime() - issuedAt.getTime();
        long lifespanInMinutes = lifespanInMillis / (60 * 1000);
 
        Assert.assertTrue(lifespanInMinutes <= 60, 
            "SECURITY FLAW: Token lifespan is too long (" + lifespanInMinutes + " mins). Max allowed is 60.");
 
        // 5. Security Assertion: No PII in Payload
        Assert.assertNull(claims.get("password"), "CRITICAL FLAW: Password exposed in JWT!");
        Assert.assertNull(claims.get("ssn"), "CRITICAL FLAW: SSN exposed in JWT!");
 
        // 6. Security Assertion: Correct Issuer
        Assert.assertEquals(claims.getIssuer(), "https://auth.mycodeyatra.com", 
            "SECURITY FLAW: Invalid Token Issuer!");
    }
}