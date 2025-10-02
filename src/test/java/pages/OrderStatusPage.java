package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderStatusPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // --- Локаторы страницы статуса ---
    private By orderNumberInput = By.xpath(".//input[@placeholder='Введите номер заказа']");
    // Кнопка "Go!"
    private By goButton = By.xpath(".//button[@class='Button_Button__ra12g Header_Button__28dPO' and text()='Go!']");
    // Сообщение об ошибке на странице трекинга (учитываем разные варианты текста)
    private By errorMessage = By.xpath(
            ".//div[(contains(@class,'Input_ErrorMessage') or contains(@class,'Track_NotFound') or contains(@class,'Order_Text')) and (contains(.,'Заказ') or contains(.,'не найден') or contains(.,'не существует') or contains(.,'что-то не так') or contains(.,'Такого заказа'))]"
    );
    // Альтернативный общий локатор текста ошибки
    private By genericErrorText = By.xpath("//*[contains(.,'Заказ') and (contains(.,'не найден') or contains(.,'не существует') or contains(.,'Такого заказа'))]");
    // Блок "не найден" на странице
    private By notFoundBlock = By.cssSelector(".Track_NotFound__6oaoY");

    public OrderStatusPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setOrderNumber(String orderNumber) {
        wait.until(ExpectedConditions.elementToBeClickable(orderNumberInput)).sendKeys(orderNumber);
    }

    public void clickGoButton() {
        wait.until(ExpectedConditions.elementToBeClickable(goButton)).click();
    }

    public boolean isErrorMessageVisible() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(errorMessage),
                    ExpectedConditions.visibilityOfElementLocated(genericErrorText),
                    ExpectedConditions.visibilityOfElementLocated(notFoundBlock)
            ));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}