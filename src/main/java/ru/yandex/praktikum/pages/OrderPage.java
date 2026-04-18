package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;

    private By nameInput = By.xpath("//input[@placeholder='* Имя']");
    private By lastNameInput = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroStationInput = By.xpath("//input[@placeholder='* Станция метро']");
    private By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[text()='Далее']");

    private By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodDropdown = By.className("Dropdown-control");
    private By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath("//button[text()='Заказать']");
    private By confirmButton = By.xpath("//button[text()='Да']");
    private By successMessage = By.className("Order_ModalHeader__3FDaJ");

    // Локатор модального окна подтверждения (само окно)
    private By confirmModal = By.className("Order_Modal__YZ-d3");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillFirstPart(String name, String lastName, String address, String metroStation, String phone) {
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(metroStationInput).sendKeys(metroStation);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + metroStation + "')]")));
        driver.findElement(By.xpath("//div[contains(text(),'" + metroStation + "')]")).click();

        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    public void fillSecondPart(String date, String rentalPeriod, String color, String comment) {
        WebElement dateElement = driver.findElement(dateInput);
        dateElement.sendKeys(date);
        dateElement.sendKeys(Keys.ENTER);

        // Явное ожидание: поле даты не пустое
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.attributeToBeNotEmpty(dateElement, "value"));

        driver.findElement(rentalPeriodDropdown).click();
        driver.findElement(By.xpath("//div[contains(@class, 'Dropdown-option') and text()='" + rentalPeriod + "']")).click();

        if (color.equals("black")) {
            driver.findElement(By.id("black")).click();
        } else if (color.equals("grey")) {
            driver.findElement(By.id("grey")).click();
        }

        driver.findElement(commentInput).sendKeys(comment);
        driver.findElement(orderButton).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Хотите оформить заказ?')]")));


        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(confirmButton));
        driver.findElement(confirmButton).click();
    }

    public boolean isOrderSuccess() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(successMessage))
                .isDisplayed();
    }
    // updated for PR
}