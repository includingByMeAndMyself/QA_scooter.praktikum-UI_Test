package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.ScooterMainPage;
import pages.YandexPage;
import pages.OrderStatusPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdditionalTest {

    private WebDriver driver;
    private ScooterMainPage mainPage;
    private YandexPage yandexPage;
    private OrderStatusPage statusPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new ScooterMainPage(driver);
        mainPage.acceptCookiesIfPresent();
        yandexPage = new YandexPage(driver);
        statusPage = new OrderStatusPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка перехода на главную по логотипу Самоката")
    void checkScooterLogoRedirect() {
        driver.get("https://qa-scooter.praktikum-services.ru/order");
        mainPage.clickScooterLogo();
        assertTrue(mainPage.isOnMainPage(), "Переход на главную страницу по логотипу Самоката не произошёл");
    }

    @Test
    @DisplayName("Проверка открытия Яндекса по логотипу")
    void checkYandexLogoOpensYandex() {
        mainPage.clickYandexLogo();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        assertTrue(yandexPage.isOnYandexPage(), "Главная страница Яндекса не открылась по логотипу");
    }

    @Test
    @DisplayName("Проверка сообщения 'Заказ не найден'")
    void checkOrderNotFound() {
        driver.get("https://qa-scooter.praktikum-services.ru/track");
        statusPage.setOrderNumber("1234567890");
        statusPage.clickGoButton();
        assertTrue(statusPage.isErrorMessageVisible(), "Сообщение 'Заказ не найден' не появилось");
    }
}