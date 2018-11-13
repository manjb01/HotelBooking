package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Page {

    protected WebDriver driver;
    private WebDriverWait wait;

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForElement(WebElement element) {
        return waitForElement(element, 10);
    }

    protected boolean isDisplayed(WebElement... element) {
        boolean displayed = false;
        for (int f = 0; f < element.length; f++) {
            displayed = elementDisplayed(element[f]);
            if (!displayed) {
                throw new IllegalStateException("Expected element was not displayed on screen. " + element[f]);
            }
        }
        return displayed;
    }

    protected boolean isPageLoaded(WebElement... element) {
        return isDisplayed(element);
    }

    protected boolean elementDisplayed(WebElement element) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        boolean displayed = false;
        try {
            if (element != null) {
                displayed = element.isDisplayed();
            }
        } catch (Exception e) {
            displayed = false;
        }
        return displayed;
    }

    protected WebElement waitForElement(By by, int timeInSeconds) {
        WebElement element = null;
        wait = new WebDriverWait(driver, timeInSeconds);
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            System.out.println("Couldn't find expected element " + by + " " + e.getMessage());
        }
        return element;
    }

    protected WebElement waitForElement(WebElement element, int timeInSeconds) {
        WebElement element1 = null;
        wait = new WebDriverWait(driver, timeInSeconds);
        try {
            element1 = wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            System.out.println("Couldn't find expected element " + element + " " + e.getMessage());
        }
        return element1;
    }

    protected boolean elementDisplayed(By by) {
        boolean displayed;
        try {
            displayed = driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            displayed = false;
        }
        return displayed;
    }

    public boolean waitForElementToDisappear(By by, int timeInSeconds) {
        boolean disappeared;
        wait = new WebDriverWait(driver, timeInSeconds);
        disappeared = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        return disappeared;

    }

}
