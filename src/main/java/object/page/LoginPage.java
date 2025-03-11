package object.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //поле "Email"
    private static final By EMAIL_FIELD = By.xpath(".//label[text()='Email']/following-sibling::input");
    //полу "Password"
    private static final By PASSWORD_FIELD = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    //кнопка "Войти"
    private static final By LOGIN_BUTTON = By.xpath(".//button[text()='Войти']");


    //Клик
    private void clickButton (By element) {
        driver.findElement(element).click();
    }

    //Ввод
    private void input(By element, String value) {
        WebElement webElement = driver.findElement(element);
        webElement.clear();
        webElement.sendKeys(value);
    }

    @Step("Ввод email")
    public void emailInput(String email) {
        input(EMAIL_FIELD, email);
    }

    @Step("Ввод пароля")
    public void passwordInput(String password) {
        input(PASSWORD_FIELD, password);
    }

    @Step("Вход в личный кабинет")
    public void loginPersonalAccount(String email, String password) {
        emailInput(email);
        passwordInput(password);
        clickButton(LOGIN_BUTTON);
    }

}
