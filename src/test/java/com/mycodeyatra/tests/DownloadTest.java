package com.mycodeyatra.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DownloadTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private File downloadFolder;

    @BeforeMethod
    public void setUp() throws IOException {
        File targetDir = new File("target");
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        // Configure custom local download folder
        downloadFolder = new File(targetDir, "downloads");
        if (!downloadFolder.exists()) {
            downloadFolder.mkdirs();
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        // Set Chrome preferences for automatic download directory
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFolder.getAbsolutePath());
        prefs.put("download.prompt_for_download", false);
        prefs.put("safebrowsing.enabled", true);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testFileDownloadAndVerify() throws InterruptedException {
        // Navigate to the live practice file download URL
        String fileUrl = "https://practice.mycodeyatra.com/#/upload-download";
        System.out.println("Navigating to live URL: " + fileUrl);
        driver.get(fileUrl);

        // Define expected custom text to write and verify
        String expectedText = "Hello MyCodeYatra Custom Download! Verification text contents.";

        // Locate the custom download text input box
        System.out.println("Locating text input box...");
        WebElement textInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@data-testid='download-text-input']"))
        );
        textInput.clear();
        textInput.sendKeys(expectedText);

        // Locating the download button
        System.out.println("Locating download button...");
        WebElement downloadBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='download-btn']"))
        );

        // Click the download link to trigger file save
        System.out.println("Clicking download button to trigger file save...");
        downloadBtn.click();

        // Poll target download directory until the file exists and download is completed
        File downloadedFile = new File(downloadFolder, "dynamic_download.txt");
        System.out.println("Polling for downloaded file at: " + downloadedFile.getAbsolutePath());
        
        boolean fileDownloaded = false;
        // Wait maximum 10 seconds (20 iterations of 500ms sleep)
        for (int i = 0; i < 20; i++) {
            if (downloadedFile.exists() && downloadedFile.length() > 0) {
                fileDownloaded = true;
                break;
            }
            Thread.sleep(500);
        }

        Assert.assertTrue(fileDownloaded, "Downloaded file was not found or remained empty!");
        System.out.println("File successfully downloaded! Size: " + downloadedFile.length() + " bytes.");
        
        // Read file contents and verify matching text
        String content = "";
        try {
            content = new String(java.nio.file.Files.readAllBytes(downloadedFile.toPath()));
        } catch (IOException e) {
            Assert.fail("Failed to read downloaded file: " + e.getMessage());
        }
        System.out.println("Downloaded Content: " + content);
        Assert.assertEquals(content, expectedText, "Downloaded file content does not match input!");

        // Clean validation completed
        System.out.println("File download test validation completed successfully!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
        // Clean up download directory
        if (downloadFolder != null && downloadFolder.exists()) {
            File[] files = downloadFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            downloadFolder.delete();
        }
    }
}
