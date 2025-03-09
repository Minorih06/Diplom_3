import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import object.page.HomePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilits.BrowserUtilits;
import utilits.ConfigReader;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class GoToBetweenSectionsParametrizedTest {
    private BrowserUtilits browserUtilits;
    private WebDriver driver;
    private  HomePage homePage;

    private By section;
    private By expectedElement;
    private boolean transitionSection; //необходимость переходной секции для активации инспектируемой секции
    private String testName;

    public GoToBetweenSectionsParametrizedTest(By section, By expectedElement, boolean transitionSection, String testName) {
        this.section = section;
        this.expectedElement = expectedElement;
        this.transitionSection = transitionSection;
        this.testName = testName;
    }

    @Parameterized.Parameters(name = "{index}: {3}")
    public static Object[][] getElements() {
        HomePage tempHomePage = new HomePage();

        return new Object[][] {
                {tempHomePage.getFILINGS(), tempHomePage.getFilingExpected(), false, "\"Начинки\""},
                {tempHomePage.getSAUCES(), tempHomePage.getSauceExpected(),false, "\"Соусы\""},
                {tempHomePage.getBUNS(), tempHomePage.getBunExpected(), true, "\"Булки\""}
        };
    }

    @Before
    public void before() {
        browserUtilits = new BrowserUtilits(ConfigReader.getProperty("browser"));
        driver = browserUtilits.getDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        driver.get(browserUtilits.getURL("HOME_PAGE"));
    }

    @Test
    @DisplayName("Тестирование перехода между секциями ингредиентов конструктора бургеров")
    public void goToBetweenSectionsTest() {
        Allure.getLifecycle().updateTestCase(tc -> tc.setName("Тестирование перехода на секцию " + testName));

        if (transitionSection) {
            homePage.clickButton(homePage.getFILINGS());
        }
        homePage.clickButton(section);
        WebElement webElement = driver.findElement(expectedElement);
        checkedViewportIngredientsSection(webElement);
    }

    @Step("Проверка видимости ингредиентов секции")
    public void checkedViewportIngredientsSection(WebElement webElement) {
        boolean isElementInViewport =
                new WebDriverWait(driver, Duration.ofSeconds(1))
                        .until(
                                driver -> {
                                    Rectangle rect = webElement.getRect();
                                    Dimension windowSize = driver.manage().window().getSize();

                                    return rect.getX() >= 0
                                            && rect.getY() >= 0
                                            && rect.getX() + rect.getWidth() <= windowSize.getWidth()
                                            && rect.getY() + rect.getHeight() <= windowSize.getHeight();
                                });

        assertTrue(isElementInViewport);
    }

    @After
    public void tearDown() {
        if (browserUtilits != null) {
            browserUtilits.tearDown();
        }
    }
}
