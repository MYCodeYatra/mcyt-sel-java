package steps;
import context.TestContext;
import io.cucumber.java.en.Given;
public class CartSteps {
    private TestContext testContext;
    // PicoContainer automatically injects the TestContext here!
    public CartSteps(TestContext testContext) {
        this.testContext = testContext;
    }
    @Given("the user adds an item to the cart")
    public void addItemToCart() {
        // Use the shared WebDriver!
        testContext.getDriver().get("https://mycodeyatra.com/store");
        // ... selenium code ...
        // Save test data to the shared context!
        testContext.setSharedOrderId("ORD-9999");
    }
}