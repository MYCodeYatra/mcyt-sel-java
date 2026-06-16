package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
public class LoginSteps {
    // Serenity automatically initializes and tears down this WebDriver!
    @Managed
    WebDriver driver;
    @Given("the user is on the login page")
    public void navigateToLogin() {
        driver.get("https://mycodeyatra.com/login");
    }
    @When("the user enters the username {string}")
    public void enterUsername(String username) {
        driver.findElement(By.id("username")).sendKeys(username);
    }
    @When("the user enters the password {string}")
    public void enterPassword(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }
    @When("the user clicks the login button")
    public void clickLogin() {
        driver.findElement(By.id("login-btn")).click();
    }
    @Then("the user should be redirected to the dashboard")
    public void verifyDashboard() {
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }
}