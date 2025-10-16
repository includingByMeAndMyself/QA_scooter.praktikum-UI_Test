package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderFormPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // --- Локаторы формы заказа (первая страница) ---
    private By firstNameInput = By.xpath(".//input[@placeholder='* Имя']");
    private By lastNameInput = By.xpath(".//input[@placeholder='* Фамилия']");
    private By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroStationInput = By.className("select-search__input");
    private By metroContainerFocused = By.cssSelector("div.select-search.has-focus");
    private By metroDropdownList = By.className("select-search__select");
    private By phoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath(".//button[contains(@class,'Button_Button') and normalize-space(text())='Далее']"); // Кнопка "Далее" на первой странице

    // --- Локаторы формы заказа (вторая страница) ---
    private By deliveryDateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodDropdown = By.className("Dropdown-root");
    private By rentalPeriodPlaceholder = By.cssSelector(".Dropdown-control .Dropdown-placeholder");
    private By rentalPeriodOption = By.xpath(".//div[@class='Dropdown-option' and text()='%s']"); // Шаблон
    private By scooterColorBlack = By.id("black");
    private By scooterColorGrey = By.id("grey");
    private By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    private By confirmYesButton = By.xpath(".//button[normalize-space(text())='Да']");
    private By confirmationModal = By.xpath(".//div[contains(@class,'Order_Modal')]");

    public OrderFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // --- Методы для заполнения первой страницы формы ---
    public void setFirstName(String firstName) {
        wait.until(ExpectedConditions.elementToBeClickable(firstNameInput)).sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void setAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    public void setMetroStation(String stationName) {
        System.out.println("OrderFormPage: Начинаем ввод станции метро '" + stationName + "'");
        WebElement inputElement = driver.findElement(metroStationInput);

        inputElement.click();
        System.out.println("OrderFormPage: Кликнули по полю ввода метро.");

        wait.until(ExpectedConditions.presenceOfElementLocated(metroContainerFocused));
        System.out.println("OrderFormPage: Контейнер метро получил класс 'has-focus'.");

        inputElement.clear();
        inputElement.sendKeys(stationName);
        System.out.println("OrderFormPage: Ввели название станции '" + stationName + "'.");

        WebElement dropdownList = wait.until(ExpectedConditions.presenceOfElementLocated(metroDropdownList));
        System.out.println("OrderFormPage: Выпадающий список метро появился: " + dropdownList.getTagName() + ", классы: " + dropdownList.getAttribute("class"));

        String optionButtonXpath = String.format(".//div[contains(@class, 'Order_Text__2broi') and contains(text(), '%s')]/parent::button", stationName);
        By stationOptionButtonLocator = By.xpath(optionButtonXpath);
        System.out.println("OrderFormPage: Ищем кнопку опции '" + stationName + "' с локатором: " + stationOptionButtonLocator.toString());
        WebElement optionButtonElement = wait.until(ExpectedConditions.elementToBeClickable(stationOptionButtonLocator));
        System.out.println("OrderFormPage: Нашли кнопку опции '" + stationName + "' в выпадающем списке.");

        optionButtonElement.click();
        System.out.println("OrderFormPage: Кликнули по кнопке опции '" + stationName + "', предполагаем, что станция выбрана.");
    }

    public void setPhone(String phone) {
        driver.findElement(phoneInput).sendKeys(phone);
    }

    public void clickNextButton() {
        WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", nextBtn);
        nextBtn.click();
        System.out.println("OrderFormPage: Кликнули кнопку 'Далее'.");
        wait.until(ExpectedConditions.presenceOfElementLocated(deliveryDateInput));
        System.out.println("OrderFormPage: Элементы второй страницы формы появились.");
    }

    // --- Методы для заполнения второй страницы формы ---
    public void setDeliveryDate(String date) {
        System.out.println("OrderFormPage: Вводим дату доставки '" + date + "'");
        WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(deliveryDateInput));
        dateInput.click();
        dateInput.clear();
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);
        System.out.println("OrderFormPage: Ввели дату доставки '" + date + "'");
    }

    public void setRentalPeriod(String period) {
        System.out.println("OrderFormPage: Выбираем срок аренды '" + period + "'");
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodDropdown));
        dropdown.click();

        String optionXpath = String.format(".//div[@class='Dropdown-option' and text()='%s']", period);
        By optionLocator = By.xpath(optionXpath);
        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        optionElement.click();
        System.out.println("OrderFormPage: Выбрали срок аренды '" + period + "'");
    }

    public void setScooterColor(String color) {
        System.out.println("OrderFormPage: Выбираем цвет самоката '" + color + "'");
        if ("black".equalsIgnoreCase(color)) {
            wait.until(ExpectedConditions.elementToBeClickable(scooterColorBlack)).click();
        } else if ("grey".equalsIgnoreCase(color)) {
            wait.until(ExpectedConditions.elementToBeClickable(scooterColorGrey)).click();
        } else {
            System.out.println("OrderFormPage: Неизвестный цвет '" + color + "', доступны: black, grey");
        }
        System.out.println("OrderFormPage: Выбрали цвет самоката '" + color + "'");
    }

    public void setComment(String comment) {
        System.out.println("OrderFormPage: Вводим комментарий '" + comment + "'");
        wait.until(ExpectedConditions.elementToBeClickable(commentInput)).sendKeys(comment);
        System.out.println("OrderFormPage: Ввели комментарий '" + comment + "'");
    }

    public void clickOrderButton() {
        System.out.println("OrderFormPage: Ожидаем появление кнопки 'Заказать' на второй странице.");
        WebElement orderBtnElement = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        System.out.println("OrderFormPage: Кнопка 'Заказать' найдена и кликабельна. Кликаем.");
        orderBtnElement.click();
    }

    public void clickConfirmYesButton() {
        System.out.println("OrderFormPage: Ожидаем появление кнопки 'Да' в модальном окне.");
        WebElement confirmBtnElement = wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", confirmBtnElement);
        System.out.println("OrderFormPage: Кнопка 'Да' найдена и кликабельна. Кликаем.");
        confirmBtnElement.click();
    }

    // --- Метод для проверки, что всплывающее окно появилось ---
    public boolean isConfirmationModalVisible() {
        System.out.println("OrderFormPage: Ожидаем появление модального окна подтверждения.");
        try {
            By successHeader = By.xpath(".//div[contains(text(),'Заказ оформлен')]");
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(confirmationModal),
                    ExpectedConditions.visibilityOfElementLocated(successHeader)
            ));
            System.out.println("OrderFormPage: Модальное окно подтверждения заказа появилось.");
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("OrderFormPage: Модальное окно подтверждения заказа НЕ появилось за отведённое время.");
            return false;
        }
    }
}