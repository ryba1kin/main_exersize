package com.bell.pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;

    private BasePage(){}
    public BasePage(WebDriver webDriver) {
        this.driver = webDriver;
    }
}
