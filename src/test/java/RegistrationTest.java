import client.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import page.*;
import model.User;
import model.UserGenerator;

import static driver.WebDriverCreator.createWebDriver;
import static org.junit.Assert.assertEquals;
import static elements.HeaderElements.TOP_CABINET_BUTTON;
import static elements.UrlList.ACCOUNT_PAGE_URL;
import static elements.UrlList.CABINET_PAGE_URL;

public class RegistrationTest {
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
    public void registrationSuccess(){
        User user = UserGenerator.getRandom();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();

        registrationPage.fillName(user.getName());
        registrationPage.fillEmail(user.getEmail());
        registrationPage.fillPassword(user.getPassword());
        registrationPage.confirmRegistration();

        registrationPage.waitForUrl(ACCOUNT_PAGE_URL);

        AccountPage accountPage = new AccountPage(driver);
        accountPage.fillEmail(user.getEmail());
        accountPage.fillPassword(user.getPassword());
        accountPage.logIn();

        MainPage mainPage = new MainPage(driver);
        mainPage.openCabinetPage(TOP_CABINET_BUTTON);
        mainPage.waitForUrl(CABINET_PAGE_URL);

        CabinetPage cabinetPage = new CabinetPage(driver);

        assertEquals("Регистрация не прошла успешно, имя пользователя не совпало", cabinetPage.getCabinetName(), user.getName());
        assertEquals("Регистрация не прошла успешно, email пользователя не совпал", cabinetPage.getCabinetEmail(), user.getEmail().toLowerCase());
    }

    @Test
    public void registrationWithPasswordLessThanSixSymbolsFail(){
        User user = UserGenerator.getRandom();
        user.setPassword("12345");

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();

        registrationPage.fillName(user.getName());
        registrationPage.fillEmail(user.getEmail());
        registrationPage.fillPassword(user.getPassword());
        registrationPage.confirmRegistration();

        assertEquals("Сообщение об ошибке не вывелось или не совпало", "Некорректный пароль", registrationPage.checkValidationError());
    }
}