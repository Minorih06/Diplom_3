package object.page;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage() {

    }

    private static final By LOGIN_ACCOUNT_BUTTON = By.xpath(".//button[text()='Войти в аккаунт']");
    private static final By PERSONAL_ACCOUNT_BUTTON = By.linkText("Личный Кабинет");

    private static final By BUILD_BURGER_TEXT = By.xpath(".//h1[text()='Соберите бургер']");

    private static final By BUNS = By.xpath(".//span[text()='Булки']");
    private static final By SAUCES = By.xpath(".//span[text()='Соусы']");
    private static final By FILINGS = By.xpath(".//span[text()='Начинки']");

    //Клик
    @Step("Клик на элемент")
    public void clickButton (By element) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).click();
    }

    @Step("Переход на страницу авторизации через кнопку \"Войти в аккаунт\"")
    public void loginAccountButtonClick() {
        clickButton(LOGIN_ACCOUNT_BUTTON);
    }

    @Step("Нажимаем на кнопку \"Личный кабинет\"")
    public void personalAccountButtonClick() {
        clickButton(PERSONAL_ACCOUNT_BUTTON);
    }

    @Step("Нажимаем на кнопку раздела конструктора бургеров")
    public void clickBurgerBuilderSection(String sectionName) {
        switch (sectionName) {
            case "Булки":
                clickButton(BUNS);
            case "Соусы":
                clickButton(SAUCES);
            case "Начинки":
                clickButton(FILINGS);
        }
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
