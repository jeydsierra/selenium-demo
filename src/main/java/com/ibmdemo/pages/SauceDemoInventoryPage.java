package com.ibmdemo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SauceDemoInventoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By pageTitle = By.xpath("//span[@class='title']");
    private By cartIcon  = By.xpath("//a[@class='shopping_cart_link']");

    public SauceDemoInventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isOnInventoryPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            return driver.findElement(pageTitle).getText().equals("Products");
        } catch (Exception e) {
            return false;
        }
    }

    public void addItemToCartByName(String itemName) {
        By addToCartButton = By.xpath(
            "//div[text()='" + itemName + "']/ancestor::div[@class='inventory_item']" +
            "//button[contains(text(),'Add to cart')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }
}