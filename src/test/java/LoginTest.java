import api.User;
import api.UserApi;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import object.page.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilits.BrowserUtilits;
import utilits.ConfigReader;

import java.time.Duration;


import static org.junit.Assert.assertEquals;


public class LoginTest {
    private BrowserUtilits browserUtilits;
    private WebDriver driver;
    private final UserApi userApi = new UserApi();
    private final Faker faker = new Faker();

    private String accessToken;

    private final String NAME = faker.name().firstName();
    private final String EMAIL = faker.internet().emailAddress();
    private final String PASSWORD = faker.internet().password();


    @Before
    public void before() {
        RestAssured.baseURI = userApi.URL;
        User user = new User(EMAIL, PASSWORD, NAME);
        Response response = userApi.createUser(user);
        accessToken = userApi.getAccessToken(response);
        browserUtilits = new BrowserUtilits(ConfigReader.getProperty("browser"));
        driver = browserUtilits.getDriver();
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Проверка входа по кнопке «Войти в аккаунт» на главной странице.")
    public void loginTheLoginToAccountButtonHomePageTest() {
        driver.get(browserUtilits.getURL("HOME_PAGE"));
        HomePage homePage = new HomePage(driver);
        homePage.loginAccountButtonClick();
        authorization();
        checkedAuthorizedUser();
    }

    @Test
    @DisplayName("Проверка входа через кнопку «Личный кабинет» на главной странице.")
    public void loginThePersonalAccountButtonHomePageTest() {
        driver.get(browserUtilits.getURL("HOME_PAGE"));
        HomePage homePage = new HomePage(driver);
        homePage.personalAccountButtonClick();
        authorization();
        checkedAuthorizedUser();
    }

    @Test
    @DisplayName("Проверка входа через «Войти» в форме регистрации.")
    public void loginTheLoginButtonRegistrationPage() {
        driver.get(browserUtilits.getURL("REGISTRATION_PAGE"));
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.loginButtonClick();
        authorization();
        checkedAuthorizedUser();
    }

    @Test
    @DisplayName("Проверка входа через «Войти» в форме восстановления пароля.")
    public void loginTheLoginButtonPasswordRecoveryPage() {
        driver.get(browserUtilits.getURL("PASSWORD_RECOVERY_PAGE"));
        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.loginButtonClick();
        authorization();
        checkedAuthorizedUser();
    }

    @Step("Авторизация")
    public void authorization() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginPersonalAccount(EMAIL, PASSWORD);
    }

    @Step("Проверяем, что пользователь авторизован")
    public void checkedAuthorizedUser() {
        HomePage homePage = new HomePage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        homePage.personalAccountButtonClick();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(personalAccountPage.getEXIT_BUTTON()));
        assertEquals(true, driver.findElement(personalAccountPage.getEXIT_BUTTON()).isDisplayed());
    }

    @After
    public void tearDown() {
        if (browserUtilits != null) {
            browserUtilits.tearDown();
        }

        userApi.deleteUser(accessToken);
    }
}
