import constants.Endpoints;
import model.User;
import api.UserApi;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import object.page.RegistrationPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilits.BrowserUtilits;
import utilits.ConfigReader;

import static org.junit.Assert.assertEquals;

public class RegistrationTest {
    private BrowserUtilits browserUtilits;
    private WebDriver driver;
    private UserApi userApi = new UserApi();
    private Faker faker = new Faker();
    private RegistrationPage registrationPage;

    private String accessToken;
    private boolean shouldDeleteUser = false;

    private final String name = faker.name().firstName();
    private final String email = faker.internet().emailAddress();
    private final String PASSWORD = faker.internet().password();

    @Before
    public void before() {
        browserUtilits = new BrowserUtilits(ConfigReader.getProperty("browser"));
        driver = browserUtilits.getDriver();
        driver.manage().window().maximize();
        driver.get(Endpoints.REGISTRATION_PAGE.toString());
        registrationPage = new RegistrationPage(driver);

    }

    @Test
    @DisplayName("Тест успешной регистрации пользователя")
    public void registrationUserTest() {
        RestAssured.baseURI = Endpoints.HOME_PAGE.toString();
        registrationPage.registration(name, email, PASSWORD);
        User user = new User(email, PASSWORD);
        Response response = userApi.loginUser(user);
        accessToken = userApi.getAccessToken(response);
        shouldDeleteUser = true;
        userApi.checkedSuccessLoginResponse(response);
    }

    @Test
    @DisplayName("Появляется ошибка \"Некорректный пароль\" при заполнении пароля длинной менее 6 символов")
    public void passwordFieldLessThenSixCharactersRegistrationUserTest() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.passwordInput(faker.internet().password(1, 5));
        driver.findElement(By.tagName("body")).click();
        checkedIsVisible();
    }

    @Step("Проверка видимости ошибки пароля объекта")
    public void checkedIsVisible() {
        assertEquals(true, registrationPage.findElementErrorPassword().isDisplayed());
    }

    @After
    public void tearDown() {
        if (browserUtilits != null) {
            browserUtilits.tearDown();
        }

        if (shouldDeleteUser) {
            userApi.deleteUser(accessToken);
        }
    }
}
