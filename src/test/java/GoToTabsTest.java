import constants.Endpoints;
import model.User;
import api.UserApi;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import object.page.HomePage;
import object.page.LoginPage;
import object.page.PersonalAccountPage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilits.BrowserUtilits;
import utilits.ConfigReader;

import java.time.Duration;

import static org.junit.Assert.*;

public class GoToTabsTest {
    private BrowserUtilits browserUtilits;
    private HomePage homePage;
    private WebDriver driver;
    private UserApi userApi = new UserApi();
    private Faker faker = new Faker();

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

        driver.get(Endpoints.HOME_PAGE.toString());

        homePage = new HomePage(driver);
        homePage.personalAccountButtonClick();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginPersonalAccount(email, password);
    }

    @Test
    @DisplayName("Проверка перехода по клику на «Личный кабинет»")
    public void goToPersonalAccountButtonClickTest() {
        homePage.personalAccountButtonClick();
        checkedGoToPage(Endpoints.PERSONAL_ACCOUNT_PAGE.toString());
    }

    @Test
    @DisplayName("Проверка перехода по клику на «Конструктор» ")
    public void goToConstructorButtonClickFromPersonalAccountTest() {
        homePage.personalAccountButtonClick();
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        personalAccountPage.constructorButtonClick();
        checkedGoToPage(Endpoints.HOME_PAGE.toString());
    }
    @Test
    @DisplayName("Проверка перехода по клику на «Stellar Burgers» ")
    public void goToStellarBurgersButtonClickFromPersonalAccountTest() {
        homePage.personalAccountButtonClick();
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        personalAccountPage.stellarBurgersButtonClick();
        checkedGoToPage(Endpoints.HOME_PAGE.toString());
    }

    @Test
    @DisplayName("Проверяем выход по кнопке «Выйти» в личном кабинете")
    public void exitPersonalAccountClickExitButtonTest() {
        homePage.personalAccountButtonClick();
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        personalAccountPage.exitPersonalAccount();
        homePage.personalAccountButtonClick();
        checkedUnauthorizedUser();
    }

    @Step("Проверяем переход")
    public void checkedGoToPage(String expectedUrl) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = driver.getCurrentUrl();
        assertEquals("Открыта не та страница!", expectedUrl, actualUrl);
    }

    @Step("Проверяем, что произошла разавторизация пользователя")
    public void checkedUnauthorizedUser() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean tempAccessToken = (boolean) wait.until(driver ->
                ((JavascriptExecutor) driver).executeScript("return localStorage.getItem('accessToken') === null;"));
        assertTrue("accessToken != null. Развторизация не произошла", tempAccessToken);
    }

    @After
    public void tearDown() {
        if (browserUtilits != null) {
            browserUtilits.tearDown();
        }

        userApi.deleteUser(accessToken);
    }
}
