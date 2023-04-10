package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CustomLogger;


import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Logger logger;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofMinutes(2)); // Wait up to 1 minute for elements to be found
        this.logger = CustomLogger.getLogger(); // Initializing logger for the class
    }

    public void emptyCart() {
        logger.log(Level.INFO, "Emptying cart");
        // Wait for the cart button and click it
        WebElement cartButton = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("[data-testid='cart-nav-link']"))));
        cartButton.click();

        try{
            // Wait for the empty cart button and click it
            WebElement emptyCartButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='main']/div[1]/div/div[1]/div/button"))));
            emptyCartButton.click();

            // Wait for the empty cart confirmation dialog to appear and click the confirmation button
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("empty-cart-title")));
            driver.switchTo().activeElement();
            WebElement confirmEmptyCart = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='td']/div[10]/div/div/div/footer/button[1]"))));
            confirmEmptyCart.click();

            // Wait for the empty cart message to appear and assert that it contains the correct text
            WebElement emptyCartText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".empty-cart__text")));
            String emptyCartMessage = emptyCartText.findElement(By.tagName("p")).getText();
            assertTrue(emptyCartMessage.contains("Your cart is empty."));
            logger.log(Level.INFO, "Cart emptied successfully");
        } catch (Exception exception){
            // If there is no empty cart button, something went wrong
            logger.log(Level.INFO, exception.getMessage());
        }
    }
}