package com.mycodeyatra.config;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.testcontainers.containers.BrowserWebDriverContainer;
 
@Configuration
public class WebDriverConfig {
 
    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrowserWebDriverContainer<?> browserContainer() {
        return new BrowserWebDriverContainer<>()
            .withCapabilities(new ChromeOptions());
    }
 
    @Bean
    @Scope("cucumber-glue") // Or "prototype" for TestNG
    public WebDriver webDriver(BrowserWebDriverContainer<?> container) {
        return new RemoteWebDriver(container.getSeleniumAddress(), new ChromeOptions());
    }
}