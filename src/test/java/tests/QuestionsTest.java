package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.ScooterMainPage;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("FAQ: проверка текста вопросов и ответов (1..8)")
    void checkFaqTexts() {
        String[] expectedQuestions = new String[] {
                "Сколько это стоит? И как оплатить?",
                "Хочу сразу несколько самокатов! Так можно?",
                "Как рассчитывается время аренды?",
                "Можно ли заказать самокат прямо на сегодня?",
                "Можно ли продлить заказ или вернуть самокат раньше?",
                "Вы привозите зарядку вместе с самокатом?",
                "Можно ли отменить заказ?",
                "Я жизу за МКАДом, привезёте?"
        };

        String[] expectedAnswers = new String[] {
                "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
                "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
                "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
                "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
                "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
                "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
                "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
                "Да, обязательно. Всем самокатов! И Москве, и Московской области."
        };

        for (int i = 1; i <= 8; i++) {
            // Проверяем текст вопроса до клика
            String actualQuestion = mainPage.getQuestionText(i);
            assertEquals(expectedQuestions[i - 1], actualQuestion, "Текст вопроса №" + i + " не совпал с эталоном");

            // Кликаем и проверяем, что ответ открылся
            mainPage.clickQuestion(i);
            assertTrue(mainPage.isAnswerVisible(i), "Ответ на вопрос №" + i + " не открылся");

            // Проверяем текст ответа
            String actualAnswer = mainPage.getAnswerText(i);
            assertEquals(expectedAnswers[i - 1], actualAnswer, "Текст ответа №" + i + " не совпал с эталоном");
        }
    }
}