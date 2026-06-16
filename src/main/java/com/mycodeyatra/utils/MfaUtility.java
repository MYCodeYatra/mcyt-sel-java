package com.mycodeyatra.utils;
 
import org.jboss.aerogear.security.otp.Totp;
 
public class MfaUtility {
 
    /**
     * Generates a 6-digit TOTP code based on the provided secret key.
     */
    public static String getTwoFactorCode(String secretKey) {
        Totp totp = new Totp(secretKey);
        return totp.now();
    }
}