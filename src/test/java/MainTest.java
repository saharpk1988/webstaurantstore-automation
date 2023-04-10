import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CartPage;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.CustomLogger;
import utils.LoggerParameterResolver;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ExtendWith(LoggerParameterResolver.class)
public class MainTest {

    private WebDriver driver;
    private final Logger logger;


    public MainTest(Logger logger){
        this.logger = CustomLogger.getLogger();
    }

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
        driver.manage().window().maximize();
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
        int pageNumber = 1;
        logger.log(Level.INFO, "Check the search results to ensure every product has the word 'Table' in its title.");
        while (searchResultsPage.isNextPagePresent()) {
            searchResultsPage.goToNextPage();
            logger.log(Level.INFO, String.format("Checking the search result on page %d", pageNumber));
            searchResultsPage.checkProductTitlesForWord("Table");
            pageNumber ++;
            logger.log(Level.INFO, String.format("Going to the search page %d", pageNumber));
        }

        //4) Add the last of found items to Cart.

        // Find the list of products
        logger.log(Level.INFO, String.format("Add the last item of found items to the Cart."));
        List<WebElement> productList = searchResultsPage.getProductContainers();
        // Find the last product in the list
        WebElement lastProduct = productList.get(productList.size() - 1);
        searchResultsPage.addToCart(lastProduct);

        // 5) Empty Cart.
        cartPage.emptyCart();
    }
}