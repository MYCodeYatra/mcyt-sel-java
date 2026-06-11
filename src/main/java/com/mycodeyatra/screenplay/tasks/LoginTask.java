package com.mycodeyatra.screenplay.tasks;

import com.mycodeyatra.screenplay.Actor;
import com.mycodeyatra.screenplay.BrowseTheWeb;
import com.mycodeyatra.screenplay.Performable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTask implements Performable {
    private final String username;
    private final String password;

    private LoginTask(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static LoginTask withCredentials(String username, String password) {
        return new LoginTask(username, password);
    }

    @Override
    public void performAs(Actor actor) {
        WebDriver driver = actor.usingAbilityTo(BrowseTheWeb.class).getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("LoginTask: " + actor.getName() + " is entering login credentials...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid='username']"))).clear();
        driver.findElement(By.xpath("//input[@data-testid='username']")).sendKeys(username);

        driver.findElement(By.xpath("//input[@data-testid='password']")).clear();
        driver.findElement(By.xpath("//input[@data-testid='password']")).sendKeys(password);

        driver.findElement(By.xpath("//button[@data-testid='login-btn']")).click();
    }
}
