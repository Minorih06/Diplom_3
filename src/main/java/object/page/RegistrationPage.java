package object.page;

import io.qameta.allure.Step;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Data
public class RegistrationPage {
    private WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    //поле "Имя"
    private final By NAME_FIELD = By.xpath(".//label[text()='Имя']/following-sibling::input");
    //поле "Email"
    private final By EMAIL_FIELD = By.xpath(".//label[text()='Email']/following-sibling::input");
    //полу "Password"
    private final By PASSWORD_FIELD = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    //кнопка "Зарегистрироваться"
    private final By REGISTRATION_BUTTON = By.xpath(".//button[text()='Зарегистрироваться']");
    //кнопка "Войти"
    private final By LOGIN_BUTTON = By.linkText("Войти");
    //ошибка "Некорректный пароль"
    private final By ERROR_PASSWORD = By.xpath(".//p[text()='Некорректный пароль']");

    //Клик
    private void clickButton(By element) {
        driver.findElement(element).click();
    }

    //Ввод
    private void input(By element, String value) {
        WebElement webElement = driver.findElement(element);
        webElement.clear();
        webElement.sendKeys(value);
    }

    @Step("Ввод имени")
    public void nameInput(String name) {
        input(NAME_FIELD, name);
    }

    @Step("Ввод email")
    public void emailInput(String email) {
        input(EMAIL_FIELD, email);
    }

    @Step("Ввод пароля")
    public void passwordInput(String password) {
        input(PASSWORD_FIELD, password);
    }

    @Step("Регистрация пользователя")
    public void registration(String name, String email, String password) {
        nameInput(name);
        emailInput(email);
        passwordInput(password);
        clickButton(REGISTRATION_BUTTON);
    }

    @Step("Нажимаем на кнопку \"Войти\"")
    public void loginButtonClick() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
        clickButton(LOGIN_BUTTON);
    }

}
