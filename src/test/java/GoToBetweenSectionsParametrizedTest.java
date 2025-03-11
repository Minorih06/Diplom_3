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

    private static final String FILLING_EXPECTED = "Биокотлета из марсианской Магнолии";
    private static final String SAUCE_EXPECTED = "Соус Spicy-X";
    private static final String BUN_EXPECTED = "Флюоресцентная булка R2-D3";

    private final String section;
    private final String expectedElement;
    private final boolean transitionSection; //необходимость переходной секции для активации инспектируемой секции
    private final String testName;

    public GoToBetweenSectionsParametrizedTest(String section, String expectedElement, boolean transitionSection, String testName) {
        this.section = section;
        this.expectedElement = expectedElement;
        this.transitionSection = transitionSection;
        this.testName = testName;
    }

    @Parameterized.Parameters(name = "{index}: {3}")
    public static Object[][] getElements() {
        return new Object[][] {
                {"Начинки", "Биокотлета из марсианской Магнолии", false, "\"Начинки\""},
                {"Соусы", "Соус Spicy-X", false, "\"Соусы\""},
                {"Булки", "Флюоресцентная булка R2-D3", true, "\"Булки\""}
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
        checkedViewportIngredientsSection(homePage.returnExpectedWebElement(expectedElement));
    }

    @Step("Проверка видимости ингредиентов секции")
    public void checkedViewportIngredientsSection(WebElement expectedElement) {
        boolean isElementInViewport =
                new WebDriverWait(driver, Duration.ofSeconds(1))
                        .until(
                                driver -> {
                                    Rectangle rect = expectedElement.getRect();
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
