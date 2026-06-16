package hooks;
import context.TestContext;
import io.cucumber.java.After;
public class GlobalHooks {
    private TestContext testContext;
    public GlobalHooks(TestContext testContext) {
        this.testContext = testContext;
    }
    @After
    public void tearDown() {
        if (testContext.getDriver() != null) {
            testContext.getDriver().quit();
        }
    }
}