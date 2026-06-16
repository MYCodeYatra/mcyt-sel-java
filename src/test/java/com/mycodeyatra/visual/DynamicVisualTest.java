package com.mycodeyatra.visual;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
 
public class DynamicVisualTest {
 
    @Test
    public void verifyDashboardWithIgnoredElements() throws Exception {
        WebDriver driver = ZapProxyManager.getProxiedDriver(); 
        driver.get("https://mycodeyatra.com/dashboard");
 
        // 1. Identify the dynamic element (e.g., a live clock or rotating ad)
        WebElement liveClock = driver.findElement(By.id("server-time-widget"));
 
        // 2. Capture the actual screenshot, explicitly ignoring the dynamic element
        Screenshot actualScreenshot = new AShot()
            .shootingStrategy(ShootingStrategies.viewportPasting(1000))
            .addIgnoredElement(By.id("server-time-widget"))
            .takeScreenshot(driver);
 
        // 3. Load the baseline
        BufferedImage expectedImage = ImageIO.read(new File("src/test/resources/baselines/dashboard.png"));
 
        // 4. Compare images using a configured ImageDiffer
        ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(expectedImage, actualScreenshot.getImage());
 
        if (diff.hasDiff()) {
            ImageIO.write(diff.getMarkedImage(), "PNG", new File("target/dashboard_diff.png"));
            Assert.fail("Visual Regression found outside of the ignored zones!");
        }
    }
}