package com.mycodeyatra.visual;
 
import org.openqa.selenium.WebDriver;
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
 
public class VisualRegressionTest {
 
    @Test
    public void verifyDashboardLayout() throws Exception {
        WebDriver driver = ZapProxyManager.getProxiedDriver(); // Assume driver setup
        driver.get("https://mycodeyatra.com/dashboard");
 
        // 1. Capture the actual live screenshot
        Screenshot actualScreenshot = new AShot()
            .shootingStrategy(ShootingStrategies.viewportPasting(1000))
            .takeScreenshot(driver);
 
        // 2. Load the baseline image from disk
        BufferedImage expectedImage = ImageIO.read(new File("src/test/resources/baselines/dashboard.png"));
 
        // 3. Compare the images using ImageDiffer
        ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(expectedImage, actualScreenshot.getImage());
 
        // 4. If there is a difference, save the Diff Image highlighting the errors
        if (diff.hasDiff()) {
            File diffFile = new File("target/visual-diffs/dashboard_diff.png");
            ImageIO.write(diff.getMarkedImage(), "PNG", diffFile);
 
            Assert.fail("VISUAL REGRESSION DETECTED! Differences found. Check diff image at: " + diffFile.getAbsolutePath());
        }
    }
}