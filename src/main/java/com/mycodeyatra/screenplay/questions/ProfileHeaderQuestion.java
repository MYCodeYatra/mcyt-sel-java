package com.mycodeyatra.screenplay.questions;

import com.mycodeyatra.screenplay.Actor;
import com.mycodeyatra.screenplay.BrowseTheWeb;
import com.mycodeyatra.screenplay.Question;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfileHeaderQuestion implements Question<String> {

    private ProfileHeaderQuestion() {}

    public static ProfileHeaderQuestion value() {
        return new ProfileHeaderQuestion();
    }

    @Override
    public String answeredBy(Actor actor) {
        WebDriver driver = actor.usingAbilityTo(BrowseTheWeb.class).getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        System.out.println("ProfileHeaderQuestion: " + actor.getName() + " is retrieving the profile welcome message...");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@data-testid='profile-title']"))).getText();
    }
}
