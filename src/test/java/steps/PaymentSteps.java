package steps;
import context.TestContext;
import io.cucumber.java.en.Then;
import org.testng.Assert;
public class PaymentSteps {
    private TestContext testContext;
    // PicoContainer injects the SAME TestContext instance!
    public PaymentSteps(TestContext testContext) {
        this.testContext = testContext;
    }
    @Then("the order is placed successfully")
    public void verifyOrder() {
        // We retrieve the Order ID that was saved by CartSteps!
        String orderId = testContext.getSharedOrderId();
        System.out.println("Verifying Payment for Order: " + orderId);
        Assert.assertEquals(orderId, "ORD-9999");
        // We use the exact same WebDriver instance!
        Assert.assertTrue(testContext.getDriver().getCurrentUrl().contains("success"));
    }
}