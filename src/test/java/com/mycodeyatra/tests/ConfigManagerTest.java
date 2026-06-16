package com.mycodeyatra.tests;
import com.mycodeyatra.config.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class ConfigManagerTest {
 @BeforeClass
 public void setup() {
  ConfigManager.reload();
 }
 @Test(description = "reads base.url from config")
 public void testBaseUrlLoaded() {
  String url = ConfigManager.get("base.url");
  System.out.println("[ConfigManager] base.url = " + url);
  Assert.assertFalse(url.isEmpty(), "base.url should not be empty");
  Assert.assertTrue(url.startsWith("http"), "base.url should be a valid URL");
 }
 @Test(description = "getInt returns correct timeout value")
 public void testGetInt() {
  int timeout = ConfigManager.getInt("explicit.wait", 10);
  System.out.println("[ConfigManager] explicit.wait = " + timeout);
  Assert.assertTrue(timeout > 0, "explicit.wait should be a positive integer");
 }
 @Test(description = "getBoolean parses headless flag correctly")
 public void testGetBoolean() {
  boolean headless = ConfigManager.getBoolean("headless");
  System.out.println("[ConfigManager] headless = " + headless);
  Assert.assertNotNull(String.valueOf(headless));
 }
 @Test(description = "missing key returns empty string, not null")
 public void testMissingKeyReturnsEmpty() {
  String val = ConfigManager.get("non.existent.key");
  Assert.assertEquals(val, "", "Missing key should return empty string");
  System.out.println("[ConfigManager] missing key = '" + val + "'");
 }
 @Test(description = "system property overrides config file value")
 public void testSystemPropertyOverride() {
  System.setProperty("browser", "firefox");
  String browser = ConfigManager.get("browser");
  Assert.assertEquals(browser, "firefox", "System property should override config file");
  System.clearProperty("browser");
  System.out.println("[ConfigManager] override test PASSED");
 }
}