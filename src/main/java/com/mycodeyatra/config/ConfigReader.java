package com.mycodeyatra.config;
 
import io.github.cdimascio.dotenv.Dotenv;
 
public class ConfigReader {
    private static final Dotenv dotenv = Dotenv.load();
 
    public static String getAdminUsername() {
        return dotenv.get("QA_ADMIN_USER");
    }
 
    public static String getAdminPassword() {
        return dotenv.get("QA_ADMIN_PASS");
    }
}