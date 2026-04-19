package ru.yandex.praktikum.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.pages.HomePage;
import ru.yandex.praktikum.pages.OrderPage;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTests extends BaseTest {
    private final String name;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String color;
    private final String comment;
    private final boolean useTopButton;

    public OrderTests(String name, String lastName, String address, String metroStation, String phone,
                      String date, String rentalPeriod, String color, String comment, boolean useTopButton) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
        this.useTopButton = useTopButton;
    }

    @Parameterized.Parameters(name = "Тест {0} {1} – кнопка {9}")
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"Иван", "Петров", "ул. Ленина, 1", "Лубянка", "89991234567",
                        "01.05.2026", "сутки", "black", "Позвоните за 5 минут", true},
                {"Мария", "Сидорова", "пр. Мира, 10", "Комсомольская", "89997654321",
                        "02.05.2026", "двое суток", "grey", "Домофон 123", false}
        };
    }

    @Test
    public void orderScooter() {
        HomePage homePage = new HomePage(driver);
        homePage.closeCookieBannerIfPresent();  // закрыть баннер, если он есть

        if (useTopButton) {
            homePage.clickOrderButtonTop();
        } else {
            homePage.clickOrderButtonBottom();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstPart(name, lastName, address, metroStation, phone);
        orderPage.fillSecondPart(date, rentalPeriod, color, comment);
        assertTrue("Заказ не был оформлен", orderPage.isOrderSuccess());
    }
}