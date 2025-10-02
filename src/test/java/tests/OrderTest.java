package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.ScooterMainPage;
import pages.OrderFormPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    private WebDriver driver;
    private ScooterMainPage mainPage;
    private OrderFormPage orderPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new ScooterMainPage(driver);
        orderPage = new OrderFormPage(driver);
        mainPage.acceptCookiesIfPresent();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"header", "footer"})
    @DisplayName("Проверка заказа через кнопку в {0}")
    void checkOrderFlowFrom(String buttonLocation) {
        if ("header".equals(buttonLocation)) {
            mainPage.clickHeaderOrderButton();
        } else if ("footer".equals(buttonLocation)) {
            mainPage.clickFooterOrderButton();
        } else {
            throw new IllegalArgumentException("Неверная точка входа: " + buttonLocation);
        }

        // Заполнение формы с данными
        orderPage.setFirstName("Иван");
        orderPage.setLastName("Иванов");
        orderPage.setAddress("ул. Пушкина, д. 10");
        orderPage.setMetroStation("Черкизовская");
        orderPage.setPhone("+71234567890");
        orderPage.clickNextButton();

        // Заполнение второй страницы формы
        orderPage.setDeliveryDate("03.10.2025");
        orderPage.setRentalPeriod("сутки");
        orderPage.setScooterColor("black");
        orderPage.setComment("Оставьте у двери");

        orderPage.clickOrderButton();
        orderPage.clickConfirmYesButton();

        assertTrue(orderPage.isConfirmationModalVisible(), "Всплывающее окно с подтверждением заказа не появилось");
    }

    // Параметризованный тест для разных наборов данных
    @ParameterizedTest
    @CsvSource(value = {
            "Анна; Петрова; ул. Ленина, д. 5; Бабушкинская; +79876543210; 03.10.2025; сутки; black; Оставьте у двери",
            "Борис; Сидоров; пр. Мира, д. 20; Курская; +79112223344; 01.01.2023; двое суток; grey; Some comment"
    }, delimiter = ';')
    @DisplayName("Проверка заказа с разными данными")
    void checkOrderFlowWithDifferentData(
            String firstName,
            String lastName,
            String address,
            String metro,
            String phone,
            String date,
            String rentalPeriod,
            String scooterColor,
            String comment) {

        mainPage.clickHeaderOrderButton();

        orderPage.setFirstName(firstName);
        orderPage.setLastName(lastName);
        orderPage.setAddress(address);
        orderPage.setMetroStation(metro);
        orderPage.setPhone(phone);
        orderPage.clickNextButton();

        // ... заполнение второй страницы ...
        orderPage.setDeliveryDate(date);
        orderPage.setRentalPeriod(rentalPeriod);
        orderPage.setScooterColor(scooterColor);
        orderPage.setComment(comment);

        orderPage.clickOrderButton();
        orderPage.clickConfirmYesButton();

        assertTrue(orderPage.isConfirmationModalVisible(), "Всплывающее окно с подтверждением заказа не появилось для данных: " + firstName + " " + lastName);
    }
}