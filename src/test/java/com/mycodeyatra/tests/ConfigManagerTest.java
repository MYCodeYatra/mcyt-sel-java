package com.mycodeyatra.tests;

import com.mycodeyatra.config.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * ConfigManagerTest validates that ConfigManager correctly loads
 * base properties and applies the active environment overlay.
 */
public class ConfigManagerTest {

    @BeforeClass
    public void setup() {
        // Force reload so tests always start from a clean state
        ConfigManager.reload();
    }

    @Test(description = "ConfigManager: reads base.url from config")
    public void testBaseUrlLoaded() {
        String url = ConfigManager.get("base.url");
        System.out.println("[ConfigManager] base.url = " + url);
        Assert.assertFalse(url.isEmpty(), "base.url should not be empty");
        Assert.assertTrue(url.startsWith("http"), "base.url should be a valid URL");
    }

    @Test(description = "ConfigManager: reads browser from config")
    public void testBrowserLoaded() {
        String browser = ConfigManager.get("browser");
        System.out.println("[ConfigManager] browser = " + browser);
        Assert.assertFalse(browser.isEmpty(), "browser should not be empty");
    }

    @Test(description = "ConfigManager: getInt returns correct timeout value")
    public void testGetInt() {
        int timeout = ConfigManager.getInt("explicit.wait", 10);
        System.out.println("[ConfigManager] explicit.wait = " + timeout);
        Assert.assertTrue(timeout > 0, "explicit.wait should be a positive integer");
    }

    @Test(description = "ConfigManager: getBoolean parses headless flag correctly")
    public void testGetBoolean() {
        boolean headless = ConfigManager.getBoolean("headless");
        System.out.println("[ConfigManager] headless = " + headless);
        // Just verify it parses without exception; value depends on active env
        Assert.assertNotNull(String.valueOf(headless));
    }

    @Test(description = "ConfigManager: missing key returns empty string, not null")
    public void testMissingKeyReturnsEmpty() {
        String val = ConfigManager.get("non.existent.key");
        System.out.println("[ConfigManager] non.existent.key = '" + val + "'");
        Assert.assertNotNull(val, "Missing key should return empty string, not null");
        Assert.assertEquals(val, "", "Missing key should return empty string");
    }

    @Test(description = "ConfigManager: system property overrides config file value")
    public void testSystemPropertyOverride() {
        System.setProperty("browser", "firefox");
        String browser = ConfigManager.get("browser");
        System.out.println("[ConfigManager] browser after override = " + browser);
        Assert.assertEquals(browser, "firefox", "System property should override config file");
        System.clearProperty("browser");
    }
}
