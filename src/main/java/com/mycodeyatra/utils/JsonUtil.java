package com.mycodeyatra.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * JsonUtil - Centralised utility for reading and parsing JSON files.
 * Wraps Jackson ObjectMapper so tests never deal with checked IOException.
 */
public class JsonUtil {

    private JsonUtil() {}

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** Parse a JSON file into a strongly-typed object. */
    public static <T> T fromFile(String filePath, Class<T> clazz) {
        try {
            return MAPPER.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            throw new RuntimeException("JsonUtil: Failed to parse JSON file -> " + filePath, e);
        }
    }

    /** Parse a JSON file into a List of Maps (useful for test data arrays). */
    public static List<Map<String, Object>> fromFileAsList(String filePath) {
        try {
            return MAPPER.readValue(new File(filePath), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("JsonUtil: Failed to parse JSON array -> " + filePath, e);
        }
    }

    /** Parse a raw JSON string into a JsonNode for dynamic field access. */
    public static JsonNode parseString(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("JsonUtil: Failed to parse JSON string", e);
        }
    }

    /** Serialise any object back to a pretty-printed JSON string. */
    public static String toJson(Object object) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException("JsonUtil: Failed to serialise object to JSON", e);
        }
    }
}
