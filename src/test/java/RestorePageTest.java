import client.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import page.CurrentPage;
import page.RestorePage;

import static driver.WebDriverCreator.createWebDriver;
import static org.junit.Assert.assertEquals;
import static elements.UrlList.ACCOUNT_PAGE_URL;

public class RestorePageTest {
    UserClient userClient = new UserClient();
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = createWebDriver();
    }
    @After
    public void cleanUp(){
        CurrentPage currentPage = new CurrentPage(driver);
        String accessToken = currentPage.getAuthToken();

        if(accessToken!=null){userClient.delete(accessToken);}
        driver.quit();
    }
    @Test
    public void returnFromRecoveryToLoginSuccessful(){
        RestorePage recoveryPage = new RestorePage(driver);
        recoveryPage.open();
        recoveryPage.returnToAccountPage();

        CurrentPage currentPage = new CurrentPage(driver);
        currentPage.waitForUrl(ACCOUNT_PAGE_URL);

        assertEquals("Не перешли на страницу логина", ACCOUNT_PAGE_URL, currentPage.getPageUrl());
    }
}
