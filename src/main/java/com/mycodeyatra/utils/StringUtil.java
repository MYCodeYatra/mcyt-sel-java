package com.mycodeyatra.utils;

/**
 * StringUtil - Centralised utility for common string manipulation
 * operations needed in test automation (sanitisation, truncation, etc.).
 */
public class StringUtil {

    private StringUtil() {}

    /** Returns true if the string is null or contains only whitespace. */
    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /** Truncate a string to maxLen characters, appending "..." if truncated. */
    public static String truncate(String value, int maxLen) {
        if (value == null) return "";
        return value.length() <= maxLen ? value : value.substring(0, maxLen) + "...";
    }

    /** Remove all whitespace (spaces, tabs, newlines) from a string. */
    public static String removeWhitespace(String value) {
        return (value == null) ? "" : value.replaceAll("\\s+", "");
    }

    /** Convert a raw string to a URL-friendly slug (lowercase, hyphen-separated). */
    public static String toSlug(String value) {
        if (value == null) return "";
        return value.trim().toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }

    /** Capitalise the first letter of each word in a sentence. */
    public static String toTitleCase(String value) {
        if (isBlank(value)) return "";
        String[] words = value.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase())
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }
}
