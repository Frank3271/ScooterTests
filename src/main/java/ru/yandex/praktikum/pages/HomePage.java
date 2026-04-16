package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    private By orderButtonTop = By.xpath("//button[text()='Заказать']");
    private By orderButtonBottom = By.xpath("(//button[text()='Заказать'])[2]");

    private By getQuestionLocator(int index) {
        return By.xpath(".//div[contains(@class, 'accordion__item')][" + (index+1) + "]//div[contains(@class, 'accordion__button')]");
    }
    private By getAnswerLocator(int index) {
        return By.xpath(".//div[contains(@class, 'accordion__item')][" + (index+1) + "]//div[contains(@class, 'accordion__panel')]");
    }

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }

    public void clickOrderButtonBottom() {
        WebElement button = driver.findElement(orderButtonBottom);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    public void clickQuestion(int index) {
        WebElement questionButton = driver.findElement(getQuestionLocator(index));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", questionButton);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(questionButton)).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(getAnswerLocator(index)));
    }

    public String getAnswerText(int index) {
        return driver.findElement(getAnswerLocator(index)).getText();
    }
}