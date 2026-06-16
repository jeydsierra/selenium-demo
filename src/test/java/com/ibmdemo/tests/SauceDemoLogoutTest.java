package com.ibmdemo.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ibmdemo.pages.SauceDemoHomePage;
import com.ibmdemo.pages.SauceDemoLoginPage;

public class SauceDemoLogoutTest extends BaseTest {

    private SauceDemoLoginPage loginPage;
    private SauceDemoHomePage homePage;

    @BeforeMethod
    public void setUp() {
        loginPage = new SauceDemoLoginPage(driver);
        homePage = new SauceDemoHomePage(driver);
        loginPage.open();
        loginPage.loginAs("standard_user", "secret_sauce");
    }

    @Test
    public void userCanLogoutSuccessfully() {
        homePage.logout();
        Assert.assertTrue(homePage.isOnLoginPage());
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
    }

    @Test
    public void loggedOutUserCannotAccessInventory() {
        homePage.logout();
        driver.get("https://www.saucedemo.com/inventory.html");
        Assert.assertTrue(homePage.isOnLoginPage());
    }
}