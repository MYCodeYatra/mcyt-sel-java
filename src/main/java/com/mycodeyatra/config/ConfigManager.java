package com.mycodeyatra.config;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class ConfigManager {
 private static final Properties PROPS = new Properties();
 private static volatile boolean initialised = false;
 private ConfigManager() {}
 public static void init() {
  if (initialised) return;
  synchronized (ConfigManager.class) {
   if (initialised) return;
   load("config.properties");
   String env = System.getProperty("env", "qa");
   load("environments/" + env + ".properties");
   initialised = true;
   System.out.println("[ConfigManager] Loaded config for env=" + env);
  }
 }
 private static void load(String resourcePath) {
  try (InputStream is = ConfigManager.class
   .getClassLoader().getResourceAsStream(resourcePath)) {
   if (is != null) {
    PROPS.load(is);
   } else {
    System.out.println("[ConfigManager] Resource not found (skipping): " + resourcePath);
   }
  } catch (IOException e) {
   throw new RuntimeException("[ConfigManager] Failed to load: " + resourcePath, e);
  }
 }
 public static String get(String key) {
  init();
  return System.getProperty(key, PROPS.getProperty(key, ""));
 }
 public static int getInt(String key, int defaultValue) {
  String val = get(key);
  try {
   return Integer.parseInt(val.trim());
  } catch (NumberFormatException e) {
   return defaultValue;
  }
 }
 public static boolean getBoolean(String key) {
  return Boolean.parseBoolean(get(key).trim());
 }
 public static void reload() {
  initialised = false;
  PROPS.clear();
  init();
 }
}