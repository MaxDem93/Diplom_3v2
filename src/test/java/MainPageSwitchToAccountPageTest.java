import client.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import page.CurrentPage;
import page.MainPage;

import static driver.WebDriverCreator.createWebDriver;
import static elements.HeaderElements.TOP_CABINET_BUTTON;
import static elements.UrlList.ACCOUNT_PAGE_URL;
import static org.junit.Assert.assertEquals;
import static page.MainPage.MIDDLE_CABINET_BUTTON;

@RunWith(Parameterized.class)
public class MainPageSwitchToAccountPageTest {
    private final String button;
    UserClient userClient = new UserClient();
    private WebDriver driver;

    public MainPageSwitchToAccountPageTest(String button) {
        this.button = button;
    }

    @Parameterized.Parameters
    public static Object[][] getSumData() {
        return new Object[][]{
                {TOP_CABINET_BUTTON},
                {MIDDLE_CABINET_BUTTON},
        };
    }

    @Before
    public void setUp() {
        driver = createWebDriver();
    }

    @Test
    public void burgerOpenAccountPageSuccessful() {
        MainPage page = new MainPage(driver);
        page.open();
        page.openCabinetPage(button);
        CurrentPage currentPage = new CurrentPage(driver);
        currentPage.waitForUrl(ACCOUNT_PAGE_URL);
        assertEquals("Не перешли на страницу логина", ACCOUNT_PAGE_URL, currentPage.getPageUrl());
    }

    @After
    public void cleanUp() {
        CurrentPage currentPage = new CurrentPage(driver);
        String accessToken = currentPage.getAuthToken();

        if (accessToken != null) {
            userClient.delete(accessToken);
        }
        driver.quit();
    }
}