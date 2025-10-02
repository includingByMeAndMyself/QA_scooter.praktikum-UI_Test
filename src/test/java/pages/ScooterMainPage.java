package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class ScooterMainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // --- Локаторы для "Вопросы о важном" ---
    // Список вопросов
    private By questionsList = By.className("accordion");
    // Каждый вопрос-ответ может быть в элементе с классом 'accordion__item'
    private By accordionItem = By.className("accordion__item");
    // Кнопка вопроса (внутри accordion__item)
    private By accordionButton = By.className("accordion__button");
    // Панель ответа (внутри accordion__item)
    private By accordionPanel = By.className("accordion__panel");
    // Кнопка "Заказать" первая
    private By headerOrderButton = By.className("Button_Button__ra12g");
    // Кнопка "Заказать" внутри .Home_FinishButton
    private By footerOrderButton = By.cssSelector(".Home_FinishButton__1_cWm .Button_Button__ra12g");
    // Логотип Самокат
    private By scooterLogo = By.className("Header_LogoScooter__3lsAR");
    // Логотип Яндекс
    private By yandexLogo = By.className("Header_LogoYandex__3TSOI");
    // Куки баннер - локатор кнопки по ID
    private By cookieAcceptButton = By.id("rcc-confirm-button");

    public ScooterMainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void acceptCookiesIfPresent() {
        System.out.println("ScooterMainPage: Проверяем наличие кнопки куки...");
        if (isElementPresent(cookieAcceptButton)) {
            System.out.println("ScooterMainPage: Кнопка куки найдена, пытаемся закрыть.");
            try {
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton));
                System.out.println("ScooterMainPage: Пытаемся кликнуть кнопку куки через JavaScript.");
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                System.out.println("ScooterMainPage: Баннер куки закрыт с помощью JavaScript клика по кнопке с ID 'rcc-confirm-button'.");// Обновлённое сообщение
            } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.ElementClickInterceptedException e) {
                System.out.println("ScooterMainPage: Не удалось кликнуть кнопку куки: " + e.getMessage());
            }
        } else {
            System.out.println("ScooterMainPage: Кнопка куки не найдена.");
        }
    }

    // Вспомогательный метод для проверки наличия элемента
    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    // --- Методы для "Вопросы о важном" ---
    public void clickQuestion(int questionNumber) {
        List<WebElement> items = driver.findElements(accordionItem);
        if (questionNumber > 0 && questionNumber <= items.size()) {
            WebElement question = items.get(questionNumber - 1);
            WebElement button = question.findElement(accordionButton);
            wait.until(ExpectedConditions.elementToBeClickable(button));
            button.click();
        } else {
            throw new IllegalArgumentException("Неверный номер вопроса: " + questionNumber);
        }
    }

    public boolean isAnswerVisible(int questionNumber) {
        List<WebElement> items = driver.findElements(accordionItem);
        if (questionNumber > 0 && questionNumber <= items.size()) {
            WebElement question = items.get(questionNumber - 1);
            WebElement panel = question.findElement(accordionPanel);
            try {
                wait.until(ExpectedConditions.visibilityOf(panel));
                return panel.isDisplayed();
            } catch (org.openqa.selenium.TimeoutException e) {
                return false;
            }
        }
        return false;
    }

    // --- Методы для заказа ---
    public void clickHeaderOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(headerOrderButton)).click();
    }

    public void clickFooterOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(footerOrderButton)).click();
    }

    // --- Методы для логотипов ---
    public void clickScooterLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(scooterLogo)).click();
    }

    public void clickYandexLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(yandexLogo)).click();
    }

    // --- Метод для проверки, что мы на главной странице ---
    public boolean isOnMainPage() {
        return driver.getCurrentUrl().equals("https://qa-scooter.praktikum-services.ru/");
    }
}