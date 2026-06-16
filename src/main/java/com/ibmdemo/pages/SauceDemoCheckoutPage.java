package com.ibmdemo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SauceDemoCheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By firstNameField  = By.xpath("//input[@id='first-name']");
    private By lastNameField   = By.xpath("//input[@id='last-name']");
    private By postalCodeField = By.xpath("//input[@id='postal-code']");
    private By continueButton  = By.xpath("//input[@id='continue']");
    private By finishButton    = By.xpath("//button[@id='finish']");
    private By successHeader   = By.xpath("//h2[@class='complete-header']");
    private By successText     = By.xpath("//div[@class='complete-text']");

    public SauceDemoCheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isOnCheckoutStepOne() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void fillShippingInfo(String firstName, String lastName, String postalCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField))
            .sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
    }

    public boolean isOrderSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successHeader));
            return driver.findElement(successHeader)
                .getText().contains("Thank you for your order");
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successText));
            return driver.findElement(successText).getText();
        } catch (Exception e) {
            return "";
        }
    }
}