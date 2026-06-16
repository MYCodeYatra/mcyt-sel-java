package com.mycodeyatra.tests;
import com.mycodeyatra.utils.FileUtil;
import com.mycodeyatra.utils.JsonUtil;
import com.mycodeyatra.utils.StringUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;
public class UtilityFrameworkTest {
 private static final String TEMP_FILE = "target/test-output/util_test.txt";
 @Test(description = "FileUtil: write and read back a file")
 public void testFileWriteAndRead() {
  String content = "Hello from FileUtil!";
  FileUtil.writeFile(TEMP_FILE, content);
  String read = FileUtil.readFile(TEMP_FILE);
  Assert.assertEquals(read, content, "File read content should match written content");
  System.out.println("[FileUtil] Write + Read PASSED: " + read);
 }
 @Test(description = "FileUtil: fileExists returns correct boolean")
 public void testFileExists() {
  FileUtil.writeFile(TEMP_FILE, "exists check");
  Assert.assertTrue(FileUtil.fileExists(TEMP_FILE));
  Assert.assertFalse(FileUtil.fileExists("target/non_existent.txt"));
  System.out.println("[FileUtil] fileExists PASSED");
 }
 @Test(description = "FileUtil: getExtension extracts file extension correctly")
 public void testGetExtension() {
  Assert.assertEquals(FileUtil.getExtension("data/users.json"), "json");
  Assert.assertEquals(FileUtil.getExtension("reports/result.xlsx"), "xlsx");
  Assert.assertEquals(FileUtil.getExtension("README"), "");
  System.out.println("[FileUtil] getExtension PASSED");
 }
 @Test(description = "JsonUtil: parse JSON string into JsonNode")
 public void testParseJsonString() {
  String json = "{\"username\":\"admin\",\"role\":\"tester\"}";
  var node = JsonUtil.parseString(json);
  Assert.assertEquals(node.get("username").asText(), "admin");
  System.out.println("[JsonUtil] parseString PASSED: username=" + node.get("username").asText());
 }
 @Test(description = "JsonUtil: serialise object to JSON string")
 public void testToJson() {
  Map<String, String> map = Map.of("env", "staging", "browser", "chrome");
  String json = JsonUtil.toJson(map);
  Assert.assertTrue(json.contains("staging"));
  System.out.println("[JsonUtil] toJson PASSED: " + json);
 }
 @Test(description = "StringUtil: isBlank detects null and whitespace")
 public void testIsBlank() {
  Assert.assertTrue(StringUtil.isBlank(null));
  Assert.assertTrue(StringUtil.isBlank("   "));
  Assert.assertFalse(StringUtil.isBlank("hello"));
  System.out.println("[StringUtil] isBlank PASSED");
 }
 @Test(description = "StringUtil: truncate limits string length")
 public void testTruncate() {
  Assert.assertEquals(StringUtil.truncate("Hello World", 5), "Hello...");
  Assert.assertEquals(StringUtil.truncate("Hi", 5), "Hi");
  System.out.println("[StringUtil] truncate PASSED");
 }
 @Test(description = "StringUtil: toSlug converts string to URL slug")
 public void testToSlug() {
  Assert.assertEquals(StringUtil.toSlug("Page Object Model"), "page-object-model");
  System.out.println("[StringUtil] toSlug PASSED");
 }
 @Test(description = "StringUtil: toTitleCase capitalises each word")
 public void testToTitleCase() {
  Assert.assertEquals(StringUtil.toTitleCase("page object model"), "Page Object Model");
  System.out.println("[StringUtil] toTitleCase PASSED");
 }
}