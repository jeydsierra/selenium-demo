package com.ibmdemo.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ibmdemo.pages.SauceDemoCartPage;
import com.ibmdemo.pages.SauceDemoCheckoutPage;
import com.ibmdemo.pages.SauceDemoHomePage;
import com.ibmdemo.pages.SauceDemoInventoryPage;
import com.ibmdemo.pages.SauceDemoLoginPage;

public class SauceDemoEndToEndTest extends BaseTest {

    private SauceDemoLoginPage loginPage;
    private SauceDemoInventoryPage inventoryPage;
    private SauceDemoCartPage cartPage;
    private SauceDemoCheckoutPage checkoutPage;
    private SauceDemoHomePage homePage;

    private static final String ITEM_NAME = "Sauce Labs Backpack";

    @BeforeMethod
    public void setUp() {
        loginPage     = new SauceDemoLoginPage(driver);
        inventoryPage = new SauceDemoInventoryPage(driver);
        cartPage      = new SauceDemoCartPage(driver);
        checkoutPage  = new SauceDemoCheckoutPage(driver);
        homePage      = new SauceDemoHomePage(driver);
    }

    @Test
    public void happyPathEndToEnd() {

        // Step 1 — Login
        loginPage.open();
        loginPage.loginAs("standard_user", "secret_sauce");
        Assert.assertTrue(
            inventoryPage.isOnInventoryPage(),
            "Step 1 FAILED: Should be on Products page after login"
        );

        // Step 2 — Add item to cart
        inventoryPage.addItemToCartByName(ITEM_NAME);
        inventoryPage.goToCart();
        Assert.assertTrue(
            cartPage.isOnCartPage(),
            "Step 2 FAILED: Should be on Cart page"
        );
        Assert.assertTrue(
            cartPage.isItemInCart(ITEM_NAME),
            "Step 2 FAILED: Item should be in cart"
        );

        // Step 3 — Proceed to checkout
        cartPage.proceedToCheckout();
        Assert.assertTrue(
            checkoutPage.isOnCheckoutStepOne(),
            "Step 3 FAILED: Should be on Checkout Step One"
        );

        // Step 4 — Fill shipping info and finish
        checkoutPage.fillShippingInfo("John", "Doe", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();
        Assert.assertTrue(
            checkoutPage.isOrderSuccessful(),
            "Step 4 FAILED: Success message should appear after finishing order"
        );
        System.out.println("Success message: " + checkoutPage.getSuccessMessage());

        // Step 5 — Logout
        homePage.logout();
        Assert.assertTrue(
            homePage.isOnLoginPage(),
            "Step 5 FAILED: Should be back on login page after logout"
        );
    }
}