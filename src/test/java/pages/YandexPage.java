package pages;

import org.openqa.selenium.WebDriver;

public class YandexPage {

    private WebDriver driver;

    public YandexPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOnYandexPage() {
        String url = driver.getCurrentUrl();
        String title = driver.getTitle();
        return (url.contains("yandex.") || title.toLowerCase().contains("яндекс"));
    }
}