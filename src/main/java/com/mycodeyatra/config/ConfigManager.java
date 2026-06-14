package com.mycodeyatra.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager - Thread-safe singleton that loads framework configuration
 * from a base config.properties, then overlays an environment-specific
 * file selected by the "env" system property (defaults to "qa").
 *
 * Usage:
 *   String url    = ConfigManager.get("base.url");
 *   boolean hl    = ConfigManager.getBoolean("headless");
 *   int timeout   = ConfigManager.getInt("explicit.wait");
 */
public class ConfigManager {

    private static final Properties PROPS = new Properties();
    private static volatile boolean initialised = false;

    private ConfigManager() {}

    public static void init() {
        if (initialised) return;
        synchronized (ConfigManager.class) {
            if (initialised) return;
            load("config.properties");                     // base defaults
            String env = System.getProperty("env", "qa"); // -Denv=staging
            load("environments/" + env + ".properties");  // env overlay
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

    /** Get a config value as String. Returns empty string if not found. */
    public static String get(String key) {
        init();
        return System.getProperty(key, PROPS.getProperty(key, ""));
    }

    /** Get a config value as int. Returns defaultValue if not found or invalid. */
    public static int getInt(String key, int defaultValue) {
        String val = get(key);
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /** Get a config value as boolean. Returns false if not found. */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key).trim());
    }

    /** Reload config (useful for tests that switch environments mid-suite). */
    public static void reload() {
        initialised = false;
        PROPS.clear();
        init();
    }
}
