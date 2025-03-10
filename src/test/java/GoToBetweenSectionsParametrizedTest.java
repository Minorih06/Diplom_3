import constants.Endpoints;
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
    private HomePage homePage;

    private static final By FILLING_EXPECTED = By.xpath(".//img[@alt='Биокотлета из марсианской Магнолии']/..");
    private static final By SAUCE_EXPECTED = By.xpath(".//img[@alt='Соус Spicy-X']/..");
    private static final By BUN_EXPECTED = By.xpath(".//img[@alt='Флюоресцентная булка R2-D3']/..");

    private final String section;
    private final By expectedElement;
    private final boolean transitionSection; //необходимость переходной секции для активации инспектируемой секции
    private final String testName;

    public GoToBetweenSectionsParametrizedTest(String section, By expectedElement, boolean transitionSection, String testName) {
        this.section = section;
        this.expectedElement = expectedElement;
        this.transitionSection = transitionSection;
        this.testName = testName;
    }

    @Parameterized.Parameters(name = "{index}: {3}")
    public static Object[][] getElements() {
        return new Object[][] {
                {"Начинки", FILLING_EXPECTED, false, "\"Начинки\""},
                {"Соусы", SAUCE_EXPECTED, false, "\"Соусы\""},
                {"Булки", BUN_EXPECTED, true, "\"Булки\""}
        };
    }

    @Before
    public void before() {
        browserUtilits = new BrowserUtilits(ConfigReader.getProperty("browser"));
        driver = browserUtilits.getDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        driver.get(Endpoints.HOME_PAGE.toString());
    }

    @Test
    @DisplayName("Тестирование перехода между секциями ингредиентов конструктора бургеров")
    public void goToBetweenSectionsTest() {
        Allure.getLifecycle().updateTestCase(tc -> tc.setName("Тестирование перехода на секцию " + testName));

        if (transitionSection) {
            homePage.clickBurgerBuilderSection("Начинки");
        }
        homePage.clickBurgerBuilderSection(section);
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
