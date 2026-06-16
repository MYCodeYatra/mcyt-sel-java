package com.mycodeyatra.utils;
 
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
 
public class WebStorageUtils {
 
    private JavascriptExecutor js;
 
    public WebStorageUtils(WebDriver driver) {
        this.js = (JavascriptExecutor) driver;
    }
 
    // --- Local Storage Methods --- //
 
    public String getLocalStorageItem(String key) {
        return (String) js.executeScript(String.format("return window.localStorage.getItem('%s');", key));
    }
 
    public void setLocalStorageItem(String key, String value) {
        js.executeScript(String.format("window.localStorage.setItem('%s','%s');", key, value));
    }
 
    // --- Session Storage Methods --- //
 
    public String getSessionStorageItem(String key) {
        return (String) js.executeScript(String.format("return window.sessionStorage.getItem('%s');", key));
    }
}