package com.mycodeyatra.utils;
public class StringUtil {
 private StringUtil() {}
 public static boolean isBlank(String value) {
  return value == null || value.trim().isEmpty();
 }
 public static String truncate(String value, int maxLen) {
  if (value == null) return "";
  return value.length() <= maxLen ? value : value.substring(0, maxLen) + "...";
 }
 public static String removeWhitespace(String value) {
  return (value == null) ? "" : value.replaceAll("\\s+", "");
 }
 public static String toSlug(String value) {
  if (value == null) return "";
  return value.trim().toLowerCase()
   .replaceAll("[^a-z0-9\\s-]", "")
   .replaceAll("\\s+", "-");
 }
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