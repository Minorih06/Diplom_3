package utilits;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


@Data
public class BrowserUtilits {

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

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}