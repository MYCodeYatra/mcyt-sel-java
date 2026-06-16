package context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class TestContext {
    private WebDriver driver;
    private String sharedOrderId;
    // The WebDriver is initialized ONCE when the Context is created
    public WebDriver getDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }
    public void setSharedOrderId(String sharedOrderId) {
        this.sharedOrderId = sharedOrderId;
    }
    public String getSharedOrderId() {
        return sharedOrderId;
    }
}