package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookingPage extends Page{

    @FindBy(id = "firstname")
    private WebElement firstName;

    @FindBy(id = "lastname")
    private WebElement surname;

    @FindBy(id = "totalprice")
    private WebElement price;

    @FindBy(id = "depositpaid")
    private WebElement deposit;

    @FindBy(id = "checkin")
    private WebElement checkIn;

    @FindBy(id = "checkout")
    private WebElement checkout;

    @FindBy(xpath = "//a[@class='ui-datepicker-prev ui-corner-all']")
    private WebElement prevMonthCalendar;

    @FindBy(xpath = "ui-datepicker-next ui-corner-all")
    private WebElement nextMonthCalendar;

    @FindBy(xpath = "//input[@value=' Save ']")
    private WebElement saveButton;
    private WebDriverWait wait;
    public BookingPage(WebDriver driver) {
        super(driver);
    }

    public BookingPage bookHotel(String firstName, String surname, String price, String deposit, String checkIn, String checkout) {
        getFirstName().sendKeys(firstName);
        getSurname().sendKeys(surname);
        getPrice().sendKeys(price);
        selectDeposit(deposit)
                .enterCheckInDate(checkIn)
                .enterCheckOutDate(checkout)
                .getSaveButton().click();
        return this;
    }
    public BookingPage bookMultipleHotel(String firstName, String surname, String price, String deposit, String checkIn, String checkout) {
        getFirstName().sendKeys(firstName);
        getSurname().sendKeys(surname);
        getPrice().sendKeys(price);
        selectDeposit(deposit);
        getCheckIn().sendKeys(checkIn);
        getCheckout().sendKeys(checkout);
        getSaveButton().click();
        return this;
    }
    public boolean isBookingSuccessful(String firstName, String surname, String price, String deposit) {
        boolean displayed = false;
        String[] values = {firstName, surname, price, deposit};
        for (String value : values) {
            String xpath = "//div[@id='bookings']//p[normalize-space()='" + firstName + "']/ancestor::div[@class='row']//p[normalize-space()='" + value + "']";
            displayed = waitForElement(By.xpath(xpath), 10).isDisplayed();
            if (!displayed) {
                throw new IllegalStateException("Expected value " + value + " not found in record of " + firstName);
            }
        }
        return displayed;
    }

    public boolean isRecordDisplayed(String firstName) {
        String userRecord = "//div[@id='bookings']//p[normalize-space()='" + firstName + "']/ancestor::div[@class='row']";
        return elementDisplayed(By.xpath(userRecord));
    }

    public BookingPage deleteBooking(String firstName) {
        String deleteButtonXpath = "//div[@id='bookings']//p[normalize-space()='" + firstName + "']/ancestor::div[@class='row']//input[@value='Delete']";
        waitForElement(By.xpath(deleteButtonXpath), 3).click();
        waitForElementToDisappear(By.xpath("//div[@id='bookings']//p[normalize-space()='" + firstName + "']"), 5);
        return this;
    }

    public BookingPage selectDeposit(String value) {
        Select deposit = new Select(getDeposit());
        deposit.selectByVisibleText(value);
        return this;
    }

    public BookingPage enterCheckInDate(String date) {
        getCheckIn().click();
        waitForElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr//a[normalize-space()='" + date + "']"), 5).click();
        return this;
    }

    public BookingPage enterCheckOutDate(String date) {
        getCheckout().click();
        waitForElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr//a[normalize-space()='" + date + "']"), 5).click();
        return this;
    }

    public boolean pageLoaded() {
        return isPageLoaded(saveButton);
    }

    public WebElement getFirstName() {
        return firstName;
    }

    public WebElement getSurname() {
        return surname;
    }

    public WebElement getPrice() {
        return price;
    }

    private WebElement getDeposit() {
        return waitForElement(deposit);
    }

    private WebElement getCheckIn() {
        return waitForElement(checkIn);
    }

    private WebElement getCheckout() {
        return waitForElement(checkout);
    }

    public WebElement getSaveButton() {
        return saveButton;
    }

}
