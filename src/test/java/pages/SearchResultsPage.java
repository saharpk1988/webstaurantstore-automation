package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchResultsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Logger logger;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofMinutes(1)); // Initializing WebDriverWait with a timeout of 1 minute
        this.logger = Logger.getLogger(SearchResultsPage.class.getName()); // Initializing logger for the class
    }

    public boolean isNextPagePresent() {
        // Finding the 'Next Page' button and checking if it contains the 'go to page' text
        return driver.findElement(By.cssSelector("#paging > nav > ul > li.inline-block.leading-4.align-top.rounded-r-md > a")).getAccessibleName().contains("go to page");
    }

    // Method to get the list of all product containers on the search result page
    public List<WebElement> getProductContainers() {
        return driver.findElements(By.id("ProductBoxContainer"));
    }

    public String getProductTile(WebElement product){
        return product.findElement(By.cssSelector("[data-testid='itemDescription']")).getText();
    }

    public void goToNextPage() {
        // Waiting for the 'Next Page' button to be visible and then clicking it
        WebElement nextPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#paging > nav > ul > li.inline-block.leading-4.align-top.rounded-r-md > a")));
        nextPage.click();
    }

    // Method to close the notification popup after adding an item to cart
    public void closeNotificationPopup() {
        // Finding the 'Close' button for the notification popup and clicking it
        WebElement closeNotificationButton = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='watnotif-wrapper']/div/p/button"))));
        closeNotificationButton.click();
        // Waiting for the 'Close' button to be invisible, i.e. for the notification popup to disappear
        wait.until(ExpectedConditions.invisibilityOf(closeNotificationButton));
        logger.log(Level.INFO, "Closing the notification popup.");
    }

    // Method to check if all product titles contain a given word
    public void checkProductTitlesForWord(String word) {
        List<WebElement> productContainers = getProductContainers();
        for (WebElement container : productContainers) {
            if (!getProductTile(container).contains(word)) {
                logger.log(Level.WARNING, String.format("Product Title \"%s\" does not contain the word '%s'.", getProductTile(container), word));
            }
        }
    }

    // Method to add a product to cart, if possible
    public void addToCart(WebElement product){
        try{
            WebElement addToCartButton = product.findElement(By.cssSelector("[data-testid='itemAddCart']"));
            addToCartButton.click();
            closeNotificationPopup();
            logger.log(Level.INFO, getProductTile(product) + " added to cart.");
        } catch(Exception exception){
            logger.log(Level.WARNING, getProductTile(product) + " can not be added to cart, it is out of stock.");
        }
    }
}
