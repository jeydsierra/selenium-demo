package com.ibmdemo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SauceDemoCartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By pageTitle      = By.xpath("//span[@class='title']");
    private By checkoutButton = By.xpath("//button[@id='checkout']");

    public SauceDemoCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isOnCartPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            return driver.findElement(pageTitle).getText().equals("Your Cart");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isItemInCart(String itemName) {
        By itemLocator = By.xpath(
            "//div[@class='inventory_item_name' and text()='" + itemName + "']"
        );
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(itemLocator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }
}