import constants.Endpoints;
import model.User;
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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilits.BrowserUtilits;
import utilits.ConfigReader;

import java.time.Duration;

import static org.junit.Assert.assertNotNull;

public class LoginTest {
    private BrowserUtilits browserUtilits;
    private WebDriver driver;
    private final UserApi userApi = new UserApi();
    private final Faker faker = new Faker();

    private String accessToken;

    private final String name = faker.name().firstName();
    private final String email = faker.internet().emailAddress();
    private final String password = faker.internet().password();

    @Before
    public void before() {
        browserUtilits = new BrowserUtilits(ConfigReader.getProperty("browser"));

        RestAssured.baseURI = Endpoints.HOME_PAGE.toString();
        User user = new User(email, password, name);
        Response response = userApi.createUser(user);
        accessToken = userApi.getAccessToken(response);

        driver = browserUtilits.getDriver();
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Проверка входа по кнопке «Войти в аккаунт» на главной странице.")
    public void loginTheLoginToAccountButtonHomePageTest() {
        driver.get(Endpoints.HOME_PAGE.toString());
        HomePage homePage = new HomePage(driver);
        homePage.loginAccountButtonClick();
        authorization();
        checkedAuthorizedUser();
    }

    @Test
    @DisplayName("Проверка входа через кнопку «Личный кабинет» на главной странице.")
    public void loginThePersonalAccountButtonHomePageTest() {
        driver.get(Endpoints.HOME_PAGE.toString());
        HomePage homePage = new HomePage(driver);
        homePage.personalAccountButtonClick();
        authorization();
        checkedAuthorizedUser();
    }

    @Test
    @DisplayName("Проверка входа через «Войти» в форме регистрации.")
    public void loginTheLoginButtonRegistrationPage() {
        driver.get(Endpoints.REGISTRATION_PAGE.toString());
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.loginButtonClick();
        authorization();
        checkedAuthorizedUser();
    }

    @Test
    @DisplayName("Проверка входа через «Войти» в форме восстановления пароля.")
    public void loginTheLoginButtonPasswordRecoveryPage() {
        driver.get(Endpoints.PASSWORD_RECOVERY_PAGE.toString());
        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.loginButtonClick();
        authorization();
        checkedAuthorizedUser();
    }

    @Step("Авторизация")
    public void authorization() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginPersonalAccount(email, password);
    }

    @Step("Проверяем, что пользователь авторизован. accessToken, полученный при регистрации, равен accessToken, полученному после авторизации в ЛК.")
    public void checkedAuthorizedUser() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String tempAccessToken = wait.until(driver ->
                (String) ((JavascriptExecutor) driver).executeScript("return localStorage.getItem('accessToken');"));
        assertNotNull("accessToken = null. Авторизация не произошла", tempAccessToken);
    }

    @After
    public void tearDown() {
        if (browserUtilits != null) {
            browserUtilits.tearDown();
        }

        userApi.deleteUser(accessToken);
    }
}
