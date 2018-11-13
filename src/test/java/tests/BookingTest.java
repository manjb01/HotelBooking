package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BookingPage;
import webUtility.BaseTest;
import webUtility.FilesOperations;
import webUtility.LogFileMessage;

import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;


public class BookingTest {

    private BaseTest baseTest = new BaseTest();
    private static WebDriver driver;
    private WebDriverWait wait;
    LogFileMessage logger = new LogFileMessage();

    BookingPage bookingPage;
    String firstname = "Michel" + String.valueOf(System.currentTimeMillis()),
            lastname = "Duglas",
            price = "456",
            deposit = "true",
            checkin = "2",
            checkout = "5";

    @BeforeClass
    public void setup() {
        try {
            driver = BaseTest.getInstance().setBrowser("chrome");
            bookingPage = new BookingPage(driver);
            baseTest.navigateToURL();
            ((JavascriptExecutor) driver).executeScript("window.focus();");
            assertTrue(bookingPage.pageLoaded());

        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.takeScreenShot();
        }
    }

    @AfterClass
    public void tearDown() {
        BaseTest.cleanUp();
    }

    @Test
    public void createNewBooking() {
        bookingPage.bookHotel(
                firstname,
                lastname,
                price,
                deposit,
                checkin,
                checkout
        );
        assertTrue(
                bookingPage.isBookingSuccessful(
                        firstname,
                        lastname,
                        price,
                        deposit
                ));
    }
    @Test(dependsOnMethods = "createNewBooking")
    public void deleteBooking() {
        bookingPage
                .deleteBooking(firstname);

        assertFalse(bookingPage.isRecordDisplayed(firstname));
    }
    @Test(description = "Booking Multiple Clients", dataProvider = "getBookingData")
    public void createMulitpleBooking(String firstName1, String surname1, String price1, String depositPaid1, String checkIn1, String checkOut1){
        firstName1= firstName1+ String.valueOf(System.currentTimeMillis());
        this.bookingPage.bookMultipleHotel(firstName1, surname1, price1, depositPaid1, checkIn1, checkOut1);
        assertTrue(
                bookingPage.isBookingSuccessful(
                        firstName1,
                        surname1,
                        price1,
                        depositPaid1
                        ));
    }
  @DataProvider
    public Object[][] getBookingData() throws Exception{
        Object[][] testObjArray = FilesOperations.getTableArray(System.getProperty("user.dir")+"/src/test/resources/TestData/CreateBookingTestData.xlsx","Sheet1");
        return (testObjArray);
    }
}
