import api.User;
import api.UserApi;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import object.page.HomePage;
import object.page.PersonalAccountPage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import utilits.BrowserUtilits;
import utilits.ConfigReader;

public class GoToTabsTest {
    private BrowserUtilits browserUtilits;
    private WebDriver driver;
    private UserApi userApi = new UserApi();
    private Faker faker = new Faker();

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

        driver.get(browserUtilits.getURL("HOME_PAGE"));
        //Передаём авторизацию на страницу
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(String.format("window.localStorage.setItem('accessToken', '%s');", accessToken));
    }

    @Test
    @DisplayName("Проверка перехода по клику на «Личный кабинет»")
    public void goToPersonalAccountButtonClickTest() {
        HomePage homePage = new HomePage(driver);
        homePage.personalAccountButtonClick();
        checkedGoToPage(browserUtilits.getURL("PERSONAL_ACCOUNT_PAGE"));
    }

    @Test
    @DisplayName("Проверка перехода по клику на «Конструктор» ")
    public void goToConstructorButtonClickFromPersonalAccountTest() {
        HomePage homePage = new HomePage(driver);
        homePage.personalAccountButtonClick();
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        personalAccountPage.constructorButtonClick();
        checkedGoToPage(browserUtilits.getURL("HOME_PAGE"));
    }
    @Test
    @DisplayName("Проверка перехода по клику на «Stellar Burgers» ")
    public void goToStellarBurgersButtonClickFromPersonalAccountTest() {
        HomePage homePage = new HomePage(driver);
        homePage.personalAccountButtonClick();
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        personalAccountPage.stellarBurgersButtonClick();
        checkedGoToPage(browserUtilits.getURL("HOME_PAGE"));
    }

    @Test
    @DisplayName("Проверяем выход по кнопке «Выйти» в личном кабинете")
    public void exitPersonalAccountClickExitButtonTest() {
        HomePage homePage = new HomePage(driver);
        homePage.personalAccountButtonClick();
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        personalAccountPage.exitPersonalAccount();
        homePage.personalAccountButtonClick();
        checkedGoToPage(browserUtilits.getURL("LOGIN_PAGE"));
    }

    @Step("Проверяем переход")
    public void checkedGoToPage(String URL) {
        driver.getCurrentUrl().equals(URL);
    }

    @After
    public void tearDown() {
        if (browserUtilits != null) {
            browserUtilits.tearDown();
        }

        userApi.deleteUser(accessToken);
    }
}
