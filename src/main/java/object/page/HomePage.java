package object.page;

import io.qameta.allure.Step;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Data
public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage() {

    }

    private final By LOGIN_ACCOUNT_BUTTON = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By PERSONAL_ACCOUNT_BUTTON = By.linkText("Личный Кабинет");

    private final By BUILD_BURGER_TEXT = By.xpath(".//h1[text()='Соберите бургер']");

    private final By BUNS = By.xpath(".//span[text()='Булки']");
    private final By SAUCES = By.xpath(".//span[text()='Соусы']");
    private final By FILINGS = By.xpath(".//span[text()='Начинки']");

    private By filingExpected = By.xpath(".//img[@alt='Биокотлета из марсианской Магнолии']/..");
    private By sauceExpected = By.xpath(".//img[@alt='Соус Spicy-X']/..");
    private By bunExpected = By.xpath(".//img[@alt='Флюоресцентная булка R2-D3']/..");

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
}
