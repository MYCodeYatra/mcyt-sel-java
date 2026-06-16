package com.mycodeyatra.screenplay.tasks;
import com.mycodeyatra.screenplay.Actor;
import com.mycodeyatra.screenplay.BrowseTheWeb;
import com.mycodeyatra.screenplay.Performable;
import org.openqa.selenium.WebDriver;
public class NavigateTask implements Performable {
    private final String url;
    private NavigateTask(String url) {
        this.url = url;
    }
    public static NavigateTask to(String url) {
        return new NavigateTask(url);
    }
    @Override
    public void performAs(Actor actor) {
        WebDriver driver = actor.usingAbilityTo(BrowseTheWeb.class).getDriver();
        System.out.println("NavigateTask: " + actor.getName() + " is navigating to: " + url);
        driver.get(url);
    }
}