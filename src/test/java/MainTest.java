import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CartPage;
import pages.HomePage;
import pages.SearchResultsPage;
import java.util.List;

public class MainTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void test() {

        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.goToHomePage();
        homePage.searchForProduct("stainless work table");

        //3) Check the search result ensuring every product has the word 'Table' in its title.
        while (searchResultsPage.isNextPagePresent()) {
            searchResultsPage.goToNextPage();
            searchResultsPage.checkProductTitlesForWord("Table");
        }

        //4) Add the last of found items to Cart.

        // Find the list of products
        List<WebElement> productList = searchResultsPage.getProductContainers();
        // Find the last product in the list
        WebElement lastProduct = productList.get(productList.size() - 1);
        searchResultsPage.addToCart(lastProduct);

        // 5) Empty Cart.
        cartPage.emptyCart();
    }
}