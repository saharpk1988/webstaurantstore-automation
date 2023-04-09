package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private final WebDriver driver;

    // Locator for the search input element
    private final By searchInput = By.name("searchval");

    // Constructor to initialize the WebDriver instance
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToHomePage() {
        // Navigate to the Webstaurantstore website
        driver.get("https://www.webstaurantstore.com/");
    }

    public void searchForProduct(String product) {
        // Find the search input element and enter the product name to search for
        WebElement searchInputElement = driver.findElement(searchInput);
        searchInputElement.sendKeys(product);
        searchInputElement.submit();
    }
}

