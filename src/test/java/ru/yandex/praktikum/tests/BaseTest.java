package ru.yandex.praktikum.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru");

        // Закрываем cookie-баннер, если он появился (увеличенное ожидание)
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            By cookieButton = By.xpath("//button[text()='Да все привыкли']");
            wait.until(ExpectedConditions.elementToBeClickable(cookieButton)).click();
        } catch (Exception e) {
            // Если баннера нет – продолжаем
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    // updated for PR
}