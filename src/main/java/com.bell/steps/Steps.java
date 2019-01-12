package com.bell.steps;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.Тогда;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Steps {
    private static final String PROPERTIES_FILE_NAME = "tests.properties";
    private static final String BY_NAME_XPATH = ".//*[text()='%s']";

    private static final By BUTTON_REGION_OK = By.xpath(".//span[text()='Да, спасибо']/parent::span");
    private static final long WEBDRIVER_WAIT_TIMEOUT = 15;
    public static final String MENU_LABEL = "Все категории";

    private WebDriver webDriver;
    private WebDriverWait wait;
    private Properties properties;

    @Before
    public void readProperties() {
        try {
            this.properties = new Properties();
            this.properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
        } catch (Exception ex) {
            throw new PendingException("невозможно прочитать свойства из файла: " + PROPERTIES_FILE_NAME);
        }
    }

    @After
    public void afterScenario() {
        webDriver.close();
        webDriver.quit();
    }


    @Пусть("^открыт браузер \"([^\"]*)\"$")
    public void openBrowser(String browserLabel) throws Throwable {
        switch (browserLabel) {
            case ("chrome"): {
                System.setProperty("webdriver.chrome.driver", this.properties.getProperty("webdriver.chrome.driver"));
                webDriver = new ChromeDriver();
                break;
            }
            default: {
                throw new PendingException("невозможно запустить браузер: " + browserLabel);
            }
        }
        webDriver.manage().timeouts().implicitlyWait(5, SECONDS);
        wait = new WebDriverWait(webDriver, WEBDRIVER_WAIT_TIMEOUT);
    }

    @И("^браузер развернут на весь экран$")
    public void maximiseBrowser() throws Throwable {
        try {
            webDriver.manage().window().maximize();
        } catch (Exception ex) {
            throw new PendingException();
        }
    }

    @Пусть("^переходим на страницу \"([^\"]*)\"$")
    public void goToPage(String uri) throws Throwable {
        try {
            webDriver.navigate().to(uri);
            regionAlert();
        } catch (Exception ex) {
            throw new PendingException("невозможно перейти на страницу: " + uri);
        }
    }

    @Когда("^пользователь кликает на раздел \"([^\"]*)\"$")
    public void clickOn(String label) throws Throwable {
        try {
            findByText(label).click();
        } catch (NoSuchElementException ex) {
            throw new PendingException("не найден раздел: " + label);
        }
    }

    @Когда("^нажимаем кнопку \"([^\"]*)\"$")
    public void нажимаемКнопку(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Тогда("^проверяем, что находимся на странице \"([^\"]*)\"$")
    public void assertCurrentUrl(String url) throws Throwable {
        Assert.assertTrue(webDriver.getCurrentUrl().contains(url));
    }
    /**
     * при желании можно добавить  функционал изменения города, пока просто подтверждаем
     */
    private void regionAlert() {
        wait.until(driver -> driver.findElement(BUTTON_REGION_OK)).click();
    }

    private WebElement findByText(String label) {
        return  webDriver.findElement(By.xpath(String.format(BY_NAME_XPATH, label)));
    }
}
