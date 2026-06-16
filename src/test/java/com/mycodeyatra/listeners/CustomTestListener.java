package com.mycodeyatra.listeners;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
public class CustomTestListener implements ITestListener {
 @Override
 public void onStart(ITestContext context) {
  System.out.println("====================================");
  System.out.println("[Listener] Suite Starting: " + context.getName());
  System.out.println("====================================");
 }
 @Override
 public void onFinish(ITestContext context) {
  System.out.println("====================================");
  System.out.println("[Listener] Suite Finished: " + context.getName());
  System.out.println("  Passed  : " + context.getPassedTests().size());
  System.out.println("  Failed  : " + context.getFailedTests().size());
  System.out.println("  Skipped : " + context.getSkippedTests().size());
  System.out.println("====================================");
 }
 @Override
 public void onTestStart(ITestResult result) {
  System.out.println("[Listener] TEST STARTED  -> " + result.getName());
 }
 @Override
 public void onTestSuccess(ITestResult result) {
  System.out.println("[Listener] TEST PASSED   -> " + result.getName());
 }
 @Override
 public void onTestFailure(ITestResult result) {
  System.out.println("[Listener] TEST FAILED   -> " + result.getName());
  System.out.println("[Listener] Failure cause : " + result.getThrowable().getMessage());
 }
 @Override
 public void onTestSkipped(ITestResult result) {
  System.out.println("[Listener] TEST SKIPPED  -> " + result.getName());
 }
 @Override
 public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
  System.out.println("[Listener] TEST PARTIALLY PASSED -> " + result.getName());
 }
}