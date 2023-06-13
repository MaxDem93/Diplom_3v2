package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static elements.UrlList.MAIN_PAGE_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MainPage {
    public static final String MIDDLE_CABINET_BUTTON = ".//button[text()='Войти в аккаунт']";
    private static final String BUN_CONSTRUCTOR_BUTTON = ".//span[text()='Булки']";
    private static final String SAUCE_CONSTRUCTOR_BUTTON = ".//span[text()='Соусы']";
    private static final String INGREDIENT_CONSTRUCTOR_BUTTON = ".//div/span[text()='Начинки']";
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(MAIN_PAGE_URL);
    }

    public void openCabinetPage(String button) {
        driver.findElement(By.xpath(button)).click();
    }

    public void waitForUrl(String url) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.urlToBe(url));
    }

    public void clickBreadButton() {
        driver.findElement(By.xpath(BUN_CONSTRUCTOR_BUTTON)).click();
    }

    public void clickSauceButton() {
        driver.findElement(By.xpath(SAUCE_CONSTRUCTOR_BUTTON)).click();
    }

    public void clickIngredientButton() {
        driver.findElement(By.xpath(INGREDIENT_CONSTRUCTOR_BUTTON)).click();
    }

    public void checkSaucesIsDisplayed() {
        assertThat("Вкладка Соусы отображается", true,
                equalTo(driver.findElement(By.xpath("//div[contains(span/text(),'Соусы') and contains(@class,'current')]")).isDisplayed()));
    }

    public void checkIngredientIsDisplayed() {
        assertThat("Вкладка Начинки отображается", true,
                equalTo(driver.findElement(By.xpath("//div[contains(span/text(),'Начинки') and contains(@class,'current')]")).isDisplayed()));
    }

    public void checkBunsIsDisplayed() {
        assertThat("Вкладка Булки отображается", true,
                equalTo(driver.findElement(By.xpath("//div[contains(span/text(),'Булки') and contains(@class,'current')]")).isDisplayed()));
    }
}