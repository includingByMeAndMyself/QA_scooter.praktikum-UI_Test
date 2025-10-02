package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.ScooterMainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionsTest {

    private WebDriver driver;
    private ScooterMainPage mainPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new ScooterMainPage(driver);
        mainPage.acceptCookiesIfPresent();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка открытия первого вопроса")
    void checkFirstQuestion() {
        int questionNumber = 1;
        mainPage.clickQuestion(questionNumber);
        assertTrue(mainPage.isAnswerVisible(questionNumber), "Ответ на первый вопрос не открылся");
    }

    @Test
    @DisplayName("Проверка открытия восьмого вопроса")
    void checkEighthQuestion() {
        int questionNumber = 8;
        mainPage.clickQuestion(questionNumber);
        assertTrue(mainPage.isAnswerVisible(questionNumber), "Ответ на восьмой вопрос не открылся");
    }
}