package utilits;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


@Data
public class BrowserUtilits {
    //главная страница
    private final String HOME_PAGE = "https://stellarburgers.nomoreparties.site/";
    //страница регистрации
    private final String REGISTRATION_PAGE = HOME_PAGE + "register";
    //страница авторизации
    private final String LOGIN_PAGE = HOME_PAGE + "login";
    //личный кабинет
    private final String PERSONAL_ACCOUNT_PAGE = HOME_PAGE + "account/profile";
    //страница восстановления пароля
    private final String PASSWORD_RECOVERY_PAGE = HOME_PAGE + "forgot-password";

    private WebDriver driver;

    public BrowserUtilits(String browserName) {
        this.driver = openBrowser(browserName);
    }

    public BrowserUtilits() {
    }

    private WebDriver openBrowser(String browserName) {
        switch (browserName) {
            case "CHROME":
                System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
                return new ChromeDriver();
            case "YANDEX":
                System.setProperty("webdriver.chrome.driver", "/usr/local/bin/yandexdriver");
                ChromeOptions options = new ChromeOptions();
                options.setBinary("/usr/bin/yandex-browser");
                return new ChromeDriver(options);
            default:
                throw new RuntimeException("Нераспознанный браузер: " + browserName);
        }
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public String getURL(String page) {
        switch (page) {
            case "HOME_PAGE":
                return HOME_PAGE;
            case "REGISTRATION_PAGE":
                return REGISTRATION_PAGE;
            case "LOGIN_PAGE":
                return LOGIN_PAGE;
            case "PERSONAL_ACCOUNT_PAGE":
                return PERSONAL_ACCOUNT_PAGE;
            case "PASSWORD_RECOVERY_PAGE":
                return PASSWORD_RECOVERY_PAGE;
            default:
                throw new RuntimeException("Нераспознанная страница: " + page);
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}