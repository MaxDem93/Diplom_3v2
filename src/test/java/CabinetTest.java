import client.UserClient;
import model.User;
import model.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page.AccountPage;
import page.CabinetPage;
import page.CurrentPage;
import page.MainPage;

import static driver.WebDriverCreator.createWebDriver;
import static elements.HeaderElements.TOP_CABINET_BUTTON;
import static elements.UrlList.*;
import static org.junit.Assert.assertEquals;

public class CabinetTest {

    UserClient userClient = new UserClient();
    private WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = createWebDriver();
    }

    @Test
    public void redirectFromCabinetToConstructorSuccess() {
        User user = UserGenerator.getRandom();
        userClient.create(user);
        AccountPage accountPage = new AccountPage(driver);
        accountPage.open();
        accountPage.fillEmail(user.getEmail());
        accountPage.fillPassword(user.getPassword());
        accountPage.logIn();
        MainPage mainPage = new MainPage(driver);
        mainPage.openCabinetPage(TOP_CABINET_BUTTON);
        mainPage.waitForUrl(CABINET_PAGE_URL);
        CabinetPage cabinetPage = new CabinetPage(driver);
        cabinetPage.openConstructorPage();
        CurrentPage currentPage = new CurrentPage(driver);
        currentPage.waitForUrl(MAIN_PAGE_URL);
        assertEquals("Не перешли на страницу конструктора", MAIN_PAGE_URL, currentPage.getPageUrl());
    }

    @Test
    public void redirectFromCabinetToMainPageSuccess() {
        User user = UserGenerator.getRandom();
        userClient.create(user);
        AccountPage accountPage = new AccountPage(driver);
        accountPage.open();
        accountPage.fillEmail(user.getEmail());
        accountPage.fillPassword(user.getPassword());
        accountPage.logIn();
        MainPage mainPage = new MainPage(driver);
        mainPage.openCabinetPage(TOP_CABINET_BUTTON);
        mainPage.waitForUrl(CABINET_PAGE_URL);
        CabinetPage cabinetPage = new CabinetPage(driver);
        cabinetPage.openMainPage();
        CurrentPage currentPage = new CurrentPage(driver);
        currentPage.waitForUrl(MAIN_PAGE_URL);
        assertEquals("Не перешли на страницу конструктора", MAIN_PAGE_URL, currentPage.getPageUrl());
    }

    @Test
    public void logoutFromCabinetSuccess() {
        User user = UserGenerator.getRandom();
        userClient.create(user);
        AccountPage accountPage = new AccountPage(driver);
        accountPage.open();
        accountPage.fillEmail(user.getEmail());
        accountPage.fillPassword(user.getPassword());
        accountPage.logIn();
        MainPage mainPage = new MainPage(driver);
        mainPage.openCabinetPage(TOP_CABINET_BUTTON);
        mainPage.waitForUrl(CABINET_PAGE_URL);
        CabinetPage cabinetPage = new CabinetPage(driver);
        cabinetPage.logOut();

        CurrentPage currentPage = new CurrentPage(driver);
        currentPage.waitForUrl(ACCOUNT_PAGE_URL);

        accountPage.fillEmail(user.getEmail());
        accountPage.fillPassword(user.getPassword());
        accountPage.logIn();

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