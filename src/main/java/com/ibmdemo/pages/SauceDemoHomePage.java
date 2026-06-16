package com.ibmdemo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SauceDemoHomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By burgerMenuButton = By.xpath("//button[@id='react-burger-menu-btn']");
    private By logoutLink       = By.xpath("//a[@id='logout_sidebar_link']");
    private By loginButton      = By.xpath("//input[@id='login-button']");

    public SauceDemoHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openBurgerMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(burgerMenuButton)).click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    public void logout() {
        openBurgerMenu();
        clickLogout();
    }

    public boolean isOnLoginPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}