package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;

    // Поля для первой формы
    private By nameInput = By.xpath("//input[@placeholder='* Имя']");
    private By lastNameInput = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroStationInput = By.xpath("//input[@placeholder='* Станция метро']");
    private By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[text()='Далее']");

    // Локатор для выбра станции метро
    private By getMetroStationOption(String stationName) {
        return By.xpath("//div[contains(text(),'" + stationName + "')]");
    }

    // Поля для второй формы
    private By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodDropdown = By.className("Dropdown-control");
    private By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath("//button[text()='Заказать']");
    private By confirmButton = By.xpath("//button[text()='Да']");
    private By successMessage = By.className("Order_ModalHeader__3FDaJ");
    private By confirmModal = By.className("Order_Modal__YZ-d3");
    private By confirmModalText = By.xpath("//div[contains(text(),'Хотите оформить заказ?')]");

    // Локаторы для чекбоксов цвета
    private By blackCheckbox = By.id("black");
    private By greyCheckbox = By.id("grey");

    // Метод для выбора периода аренды
    private By getRentalPeriodOption(String period) {
        return By.xpath("//div[contains(@class, 'Dropdown-option') and text()='" + period + "']");
    }

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillFirstPart(String name, String lastName, String address, String metroStation, String phone) {
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(metroStationInput).sendKeys(metroStation);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(getMetroStationOption(metroStation)));
        driver.findElement(getMetroStationOption(metroStation)).click();

        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    public void fillSecondPart(String date, String rentalPeriod, String color, String comment) {
        WebElement dateElement = driver.findElement(dateInput);
        dateElement.sendKeys(date);
        dateElement.sendKeys(Keys.ENTER);

        // Ожидание, что дата заполнилась
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.attributeToBeNotEmpty(dateElement, "value"));

        driver.findElement(rentalPeriodDropdown).click();
        driver.findElement(getRentalPeriodOption(rentalPeriod)).click();

        // Выбор цвета черз вынесенные локаторы
        if (color.equals("black")) {
            driver.findElement(blackCheckbox).click();
        } else if (color.equals("grey")) {
            driver.findElement(greyCheckbox).click();
        }

        driver.findElement(commentInput).sendKeys(comment);
        driver.findElement(orderButton).click();

        // Ожидание появления модального окна (локатор вынесен в поле confirmModalText)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(confirmModalText));

        // Ожидание кликабельности кнопки "Да"
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(confirmButton));
        driver.findElement(confirmButton).click();
    }

    public boolean isOrderSuccess() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(successMessage))
                .isDisplayed();
    }
    //доп
}