package com.mycodeyatra.utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
public class JsonUtil {
 private JsonUtil() {}
 private static final ObjectMapper MAPPER = new ObjectMapper();
 public static <T> T fromFile(String filePath, Class<T> clazz) {
  try {
   return MAPPER.readValue(new File(filePath), clazz);
  } catch (IOException e) {
   throw new RuntimeException("JsonUtil: Failed to parse JSON file -> " + filePath, e);
  }
 }
 public static List<Map<String, Object>> fromFileAsList(String filePath) {
  try {
   return MAPPER.readValue(new File(filePath), new TypeReference<>() {});
  } catch (IOException e) {
   throw new RuntimeException("JsonUtil: Failed to parse JSON array -> " + filePath, e);
  }
 }
 public static JsonNode parseString(String json) {
  try {
   return MAPPER.readTree(json);
  } catch (IOException e) {
   throw new RuntimeException("JsonUtil: Failed to parse JSON string", e);
  }
 }
 public static String toJson(Object object) {
  try {
   return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
  } catch (IOException e) {
   throw new RuntimeException("JsonUtil: Failed to serialise object to JSON", e);
  }
 }
}