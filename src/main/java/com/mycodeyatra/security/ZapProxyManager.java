package com.mycodeyatra.security;
 
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
 
public class ZapProxyManager {
 
    public static WebDriver getProxiedDriver() {
 
        // Define the ZAP Proxy address
        String zapProxyAddress = "localhost:8080";
 
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(zapProxyAddress);
        proxy.setSslProxy(zapProxyAddress);
 
        // Inject the proxy into ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setProxy(proxy);
 
        // Ignore certificate errors because ZAP uses its own self-signed root cert
        options.setAcceptInsecureCerts(true);
 
        return new ChromeDriver(options);
    }
}