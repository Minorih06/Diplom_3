package object.page;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private static final By LOGIN_ACCOUNT_BUTTON = By.xpath(".//button[text()='Войти в аккаунт']");
    private static final By PERSONAL_ACCOUNT_BUTTON = By.linkText("Личный Кабинет");

    private static final By BUNS = By.xpath(".//span[text()='Булки']");
    private static final By SAUCES = By.xpath(".//span[text()='Соусы']");
    private static final By FILINGS = By.xpath(".//span[text()='Начинки']");

    private static final By MAGNOLIA_BIO_CUTLET = By.xpath(".//img[@alt='Биокотлета из марсианской Магнолии']/..");
    private static final By SAUCE_SPICY_X = By.xpath(".//img[@alt='Соус Spicy-X']/..");
    private static final By FLUORESCENT_BUN_R2_D3 = By.xpath(".//img[@alt='Флюоресцентная булка R2-D3']/..");

    public WebElement findElement (By element) {
        return driver.findElement(element);
    }

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
    }

    @Step("Находим проверяемый элемент")
    public WebElement returnExpectedWebElement(String elementName) {
        switch (elementName) {
            case "Биокотлета из марсианской Магнолии":
                return findElement(MAGNOLIA_BIO_CUTLET);
            case "Соус Spicy-X":
                return findElement(SAUCE_SPICY_X);
            case "Флюоресцентная булка R2-D3":
                return findElement(FLUORESCENT_BUN_R2_D3);
            default:
                throw new IllegalArgumentException("Неизвестный элемент: " + elementName);
        }
    }
}
