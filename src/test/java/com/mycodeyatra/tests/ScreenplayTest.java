package com.mycodeyatra.tests;
import com.mycodeyatra.driver.DriverFactory;
import com.mycodeyatra.screenplay.Actor;
import com.mycodeyatra.screenplay.BrowseTheWeb;
import com.mycodeyatra.screenplay.questions.ProfileHeaderQuestion;
import com.mycodeyatra.screenplay.tasks.LoginTask;
import com.mycodeyatra.screenplay.tasks.NavigateTask;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
public class ScreenplayTest {
    private WebDriver driver;
    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.initDriver(browser);
        driver.manage().window().maximize();
    }
    @Test
    public void testSuccessfulLoginViaScreenplay() {
        // 1. Instantiating Actor with Abilities
        Actor james = Actor.named("James").can(BrowseTheWeb.with(driver));
        // 2. Perform Tasks
        james.attemptsTo(
                NavigateTask.to("https://practice.mycodeyatra.com/#/login"),
                LoginTask.withCredentials("admin", "admin123")
        );
        // 3. Ask Questions to assert results
        String welcomeMessage = james.asksFor(ProfileHeaderQuestion.value());
        System.out.println("[ScreenplayTest] Welcome Message Retrieved: " + welcomeMessage);
        Assert.assertTrue(welcomeMessage.contains("Welcome back, admin!"), 
                "Profile welcome page validation failed in Screenplay run!");
    }
    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}