package com.ibmdemo.tests;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ibmdemo.pages.SauceDemoHomePage;
import com.ibmdemo.pages.SauceDemoLoginPage;

public class SauceDemoLogoutTest {

    private WebDriver driver;
    private SauceDemoLoginPage loginPage;
    private SauceDemoHomePage homePage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--incognito");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.default_content_setting_values.notifications", 2,
                "autofill.profile_enabled", false));
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        loginPage = new SauceDemoLoginPage(driver);
        homePage = new SauceDemoHomePage(driver);

        // log in before each test
        loginPage.open();
        loginPage.loginAs("standard_user", "secret_sauce");
    }

    @Test
    public void userCanLogoutSuccessfully() {
        homePage.logout();

        Assert.assertTrue(
                homePage.isOnLoginPage(),
                "User should be redirected to login page after logout");
        Assert.assertTrue(
                driver.getCurrentUrl().equals("https://www.saucedemo.com/"),
                "URL should return to saucedemo home after logout");
    }

    @Test
    public void loggedOutUserCannotAccessInventory() {
        homePage.logout();

        // try to go back to inventory directly
        driver.get("https://www.saucedemo.com/inventory.html");

        Assert.assertTrue(
                homePage.isOnLoginPage(),
                "Logged out user should be redirected to login page when accessing inventory");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}