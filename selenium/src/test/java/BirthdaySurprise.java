import POM.BookFlightPage;
import POM.LoginPage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import org.testng.Reporter;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Listeners(Utilities.ListenerTest.class)

public class BirthdaySurprise extends BookFlightPage {
    public BookFlightPage bookFlightPage;
    boolean result;


    public String generateDate(int days) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, days);
        String newDate = dateFormat.format(c.getTime());
        return newDate;
    }

    @BeforeMethod
    public void setUp() {
        driver = getDriver();
        bookFlightPage = new BookFlightPage();

    }

    @Test(description = "Raj books flight tickets to surprise his wife" , priority = 0)
    public void willTheSurpriseWork() throws InterruptedException, IOException, InvalidFormatException {
        bookFlightPage.Login("https://www.cleartrip.com/");
        Reporter.log("Logged in to ClearTrip.com");
        bookFlightPage.enterDate(generateDate(14));
        bookFlightPage.enterFromAndTo("Bangalore, IN - Kempegowda International Airport (BLR)", "Hyderabad, IN - Rajiv Gandhi International (HYD)");
        bookFlightPage.selectBasedOnOptions("Childrens", "1");
        Reporter.log("Flight booked for: "+ generateDate(14) + "From: Bangalore, IN - Kempegowda International Airport (BLR) to Hyderabad, IN - Rajiv Gandhi International (HYD)");
        Reporter.log("Travellers 1 Adult + 1 Child");
        result = bookFlightPage.searchFlights();
        Assert.assertTrue(result, "Flights are available to fly!!!");
        bookFlightPage.selectFlight();
        Reporter.log("Booking Non-Stop Flight with the lowest price" + BookFlightPage.amount);
        bookFlightPage.writeToExcel("/src/main/resources/HybdExpenses.xlsx", 0, BookFlightPage.amount);
        result = result && bookFlightPage.lastStepsOfBooking("rajsundar454@mailinator.com");
        Assert.assertTrue(result, "Enjoy surprising your wife - Raj");
        Reporter.log("Booked!!! Enjoy surprising your wife - Raj:)");
        Reporter.log("Expense Report: resources/HybdExpenses.html" );
    }

    @Test(description = "Surprise ends and the couple return back", priority = 1)
    public void surpriseEndsHappily() throws InterruptedException, IOException, InvalidFormatException {
        bookFlightPage.Login("https://www.cleartrip.com/");
        Reporter.log("Logged in to ClearTrip.com");
        bookFlightPage.enterDate(generateDate(15));
        bookFlightPage.enterFromAndTo("Hyderabad, IN - Rajiv Gandhi International (HYD)", "Bangalore, IN - Kempegowda International Airport (BLR)");
        bookFlightPage.selectBasedOnOptions("Adults", "2");
        Reporter.log("Flight booked for: "+ generateDate(15) + "From: Hyderabad, IN - Rajiv Gandhi International (HYD) to Bangalore, IN - Kempegowda International Airport (BLR)");
        Reporter.log("Travellers 2 Adult(s)");
        result = bookFlightPage.searchFlights();
        Assert.assertTrue(result, "Flights are available to fly!!!");
        bookFlightPage.selectFlight();
        Reporter.log("Booking Non-Stop Flight with the lowest price" + BookFlightPage.amount);
        bookFlightPage.writeToExcel("/src/main/resources/HybdExpenses.xlsx", 1, BookFlightPage.amount);
        result = result && bookFlightPage.lastStepsOfBooking("rajsundar454@mailinator.com");
        Assert.assertTrue(result, "Enjoy returning back to your native");
        Reporter.log("Enjoy returning back to your native");
        Reporter.log("Expense Report: /resources/HybdExpenses.html" );
    }

    @AfterMethod
    public void setUpClose() {
        driver.quit();
        driver = new ChromeDriver();

    }
}