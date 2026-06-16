// src/test/java/com/mycodeyatra/karate/generators/DataGenerator.java
package com.mycodeyatra.karate.generators;
import java.util.UUID;
public class DataGenerator {
    public static String getRandomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@mycodeyatra.com";
    }
}