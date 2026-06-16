package com.mycodeyatra.visual;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
 
import javax.imageio.ImageIO;
import java.io.File;
 
public class BaselineGenerator {
 
    public static void captureBaseline() throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.get("https://mycodeyatra.com/dashboard");
 
        // Take a full page screenshot by scrolling every 1000ms
        Screenshot baseline = new AShot()
            .shootingStrategy(ShootingStrategies.viewportPasting(1000))
            .takeScreenshot(driver);
 
        // Save the baseline image to the project directory
        ImageIO.write(baseline.getImage(), "PNG", new File("src/test/resources/baselines/dashboard.png"));
 
        driver.quit();
    }
}