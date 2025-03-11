package object.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountPage {
    private WebDriver driver;

    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    //кнопка "Выход"
    private static final By EXIT_BUTTON = By.xpath(".//button[text()='Выход']");

    private static final By STELLAR_BURGERS_BUTTON = By.className("AppHeader_header__logo__2D0X2");
    private static final By CONSTRUCTOR_BUTTON = By.xpath(".//p[text()='Конструктор']");

    //Клик
    private void clickButton (By element) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).click();
    }

    @Step("Нажимаем на кнопку \"Конструктор\"")
    public void constructorButtonClick() {
        clickButton(CONSTRUCTOR_BUTTON);
    }

    @Step("Нажимаем на кнопку \"Stellar Burgers\"")
    public void stellarBurgersButtonClick() {
        clickButton(STELLAR_BURGERS_BUTTON);
    }

    @Step("Выход из личного кабинета")
    public void exitPersonalAccount() {
        clickButton(EXIT_BUTTON);
    }
}


