package com.mycodeyatra.utils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class FileUtil {
 private FileUtil() {}
 public static String readFile(String filePath) {
  try {
   return Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
  } catch (IOException e) {
   throw new RuntimeException("FileUtil: Failed to read file -> " + filePath, e);
  }
 }
 public static void writeFile(String filePath, String content) {
  try {
   Path path = Paths.get(filePath);
   Files.createDirectories(path.getParent());
   Files.writeString(path, content, StandardCharsets.UTF_8);
  } catch (IOException e) {
   throw new RuntimeException("FileUtil: Failed to write file -> " + filePath, e);
  }
 }
 public static boolean fileExists(String filePath) {
  return Files.exists(Paths.get(filePath));
 }
 public static String getExtension(String filePath) {
  String name = Paths.get(filePath).getFileName().toString();
  int dot = name.lastIndexOf('.');
  return (dot > 0) ? name.substring(dot + 1) : "";
 }
}