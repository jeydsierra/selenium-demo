package com.ibmdemo.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ibmdemo.pages.SauceDemoLoginPage;

public class SauceDemoLoginTest {

    private WebDriver driver;
    private SauceDemoLoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        loginPage = new SauceDemoLoginPage(driver);
        loginPage.open();
    }

    @Test
    public void validLoginRedirectsToProductsPage() {
        loginPage.loginAs("standard_user", "secret_sauce");

        Assert.assertTrue(
            loginPage.isLoginSuccessful(),
            "User should be redirected to Products page after valid login"
        );
        Assert.assertTrue(
            loginPage.getCurrentUrl().contains("inventory"),
            "URL should contain 'inventory' after successful login"
        );
    }

    @Test
    public void lockedOutUserSeesErrorMessage() {
        loginPage.loginAs("locked_out_user", "secret_sauce");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
            error.contains("locked out"),
            "Locked out user should see an error message"
        );
    }

    @Test
    public void emptyUsernameShowsError() {
        loginPage.loginAs("", "secret_sauce");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
            error.contains("Username is required"),
            "Empty username should show validation error"
        );
    }

    @Test
    public void emptyPasswordShowsError() {
        loginPage.loginAs("standard_user", "");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
            error.contains("Password is required"),
            "Empty password should show validation error"
        );
    }

    @Test
    public void wrongPasswordShowsError() {
        loginPage.loginAs("standard_user", "wrongpassword");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
            error.contains("Username and password do not match"),
            "Wrong password should show credential mismatch error"
        );
    }

    @Test(dataProvider = "validUsers")
    public void multipleValidUsersCanLogin(String username, String password) {
        loginPage.loginAs(username, password);

        Assert.assertTrue(
            loginPage.isLoginSuccessful(),
            username + " should be able to log in successfully"
        );

        loginPage.open();
    }

    @DataProvider(name = "validUsers")
    public Object[][] validUsers() {
        return new Object[][] {
            {"standard_user",  "secret_sauce"},
            {"problem_user",   "secret_sauce"},
            {"performance_glitch_user", "secret_sauce"}
        };
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}