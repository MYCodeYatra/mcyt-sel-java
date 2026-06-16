package com.yourcompany;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class FirstTest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100));
    }

    @BeforeEach
    void createContext() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @Test
    void pageTitleShouldMatchExpected() {
        page.navigate("https://playwright.dev/java/");
        assertEquals("Playwright for Java", page.title());
    }

    @Test
    void getStartedLinkShouldNavigateToDocs() {
        page.navigate("https://playwright.dev/java/");
        page.getByText("Get started").click();
        assertTrue(page.url().contains("docs/intro"));
    }
}