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
public class UploadTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private File uploadTxtFile;
    @BeforeMethod
    public void setUp() throws IOException {
        File targetDir = new File("target");
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        // Create dummy text file to upload
        uploadTxtFile = new File(targetDir, "upload_test_file.txt");
        try (FileWriter writer = new FileWriter(uploadTxtFile)) {
            writer.write("This is dummy text content for file upload test validation.");
        }
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void testFileUploadViaSendKeys() {
        // Navigate to the live practice file upload URL
        String fileUrl = "https://practice.mycodeyatra.com/#/upload-download";
        System.out.println("Navigating to live URL: " + fileUrl);
        driver.get(fileUrl);
        // Locating the file upload input element
        System.out.println("Locating upload input field...");
        WebElement uploadInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-testid='file-upload-input']"))
        );
        // Upload using sendKeys strategy with absolute file path
        String uploadFilePath = uploadTxtFile.getAbsolutePath();
        System.out.println("Uploading file: " + uploadFilePath);
        uploadInput.sendKeys(uploadFilePath);
        // Verify status changes to include the uploaded file name
        WebElement statusElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-testid='upload-status']"))
        );
        String statusText = statusElement.getText();
        System.out.println("Status Message Captured: " + statusText);
        Assert.assertTrue(statusText.contains("Successfully uploaded: upload_test_file.txt"), 
                "File upload status validation failed!");
        System.out.println("File upload test validation completed successfully!");
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Quitting driver session...");
            driver.quit();
        }
        // Clean up temporary files
        if (uploadTxtFile != null && uploadTxtFile.exists()) {
            uploadTxtFile.delete();
        }
    }
}