package object.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PasswordRecoveryPage {
    private WebDriver driver;

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    //кнопка "Войти"
    private final By LOGIN_BUTTON = By.linkText("Войти");


    private void clickButton (By element) {
        driver.findElement(element).click();
    }

    @Step("Нажимаем на кнопку \"Войти\"")
    public void loginButtonClick() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
        clickButton(LOGIN_BUTTON);
    }
}
