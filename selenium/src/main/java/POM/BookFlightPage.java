package POM;

import Utilities.Utilities;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

public class BookFlightPage extends Utilities {

    public BookFlightPage() {
        driver = getDriver();
        PageFactory.initElements(driver, this);
    }

    public static String amount;


    @FindBy(id = "DepartDate")
    public WebElement _departDate;

    @FindBy(id = "FromTag")
    public WebElement _fromTag;

    @FindBy(id = "ToTag")
    public WebElement _toTag;

    @FindBy(xpath = "//span/parent::div/following-sibling::div/div/p[text()='Non-stop']")
    public WebElement _nonStop;

    @FindBy(xpath = ".//input[@type='checkbox'][@value='0']/following-sibling::label")
    public WebElement _nonStopV1;

    @FindBy(xpath = "//a/span[text()='Price']")
    public WebElement _price;

    @FindBy(xpath = ".//div/div/div[@class='ow-tuple-container__details ms-grid-row-2']/div/button[text()='Book']")
    public List<WebElement> _bookButton;

    @FindBy(xpath = ".//tr/td/button[contains(text(),'Book')]")
    public List<WebElement> _bookButtonV1;

    @FindBy(xpath = ".//input[@value='Continue booking']")
    public WebElement _continueButton;

    @FindBy(id = "username")
    public WebElement _emailAddress;

    @FindBy(xpath = ".//input[@value='Continue']")
    public WebElement _justContinueButton;

    @FindBy(id = "AdultFname1")
    public WebElement _adultFirstName;

    @FindBy(id = "AdultLname1")
    public WebElement _adultLastName;

    @FindBy(id = "ChildFname1")
    public WebElement _childFirstName;

    @FindBy(id = "ChildLname1")
    public WebElement _childLastName;

    @FindBy(xpath = "//div[@id='intADDAD1']//span[@class='countryAutoCompleteHolder']/input[@type='text'][@data-name='nationality']")
    public WebElement _adultNationality;

    @FindBy(xpath = "//div[@id='intADDCD1']//span[@class='countryAutoCompleteHolder']/input[@type='text'][@data-name='nationality']")
    public WebElement _childNationality;

    @FindBy(id = "mobileNumber")
    public WebElement _mobileNumber;

    @FindBy(id = "AdultTitle1")
    public WebElement _selectAdultTitle;

    @FindBy(id = "ChildTitle1")
    public WebElement _selectChildTitle;

    @FindBy(id = "SearchBtn")
    public WebElement _searchFlights;

    public void Login(String url) throws InterruptedException {
        driver.get(url);
        driver.navigate().refresh();
        Utilities.explicitwait("//form/h1[text()='Search flights']");

    }

    public void enterDate(String date) throws InterruptedException {
       _departDate.sendKeys(date);
    }

    public void enterFromAndTo(String from,String to) throws InterruptedException {
        _fromTag.sendKeys(from);
        _toTag.sendKeys(to);
    }

    public boolean searchFlights() throws InterruptedException {
        _searchFlights.click();
        try{
            explicitwait("//span/parent::div/following-sibling::div/div/p[text()='Non-stop']");
            return _bookButton.size() > 0;
        }catch (Exception e){
            try{
                explicitwait(".//input[@type='checkbox'][@value='0']/following-sibling::label");
                return _bookButtonV1.size() > 0;
            }
            catch (Exception ex){
                return false;
            }
        }

    }

    public void selectFlight() throws InterruptedException {
        try{
            if(_nonStop.isDisplayed()){
                selectFlightV2();
            }else{
                selectFlightV1();
            }
        }catch (Exception e){
            selectFlightV1();
        }

    }

    public void selectFlightV1() throws InterruptedException {
        explicitwait(".//input[@type='checkbox'][@value='0']/following-sibling::label");
       _nonStopV1.click();
        amount = driver.findElement(By.xpath("(.//th[@class='price']/span[1])")).getAttribute("data-pr");
        _bookButtonV1.get(0).click();
    }

    public void selectFlightV2() throws InterruptedException {
        explicitwait("//span/parent::div/following-sibling::div/div/p[text()='Non-stop']");
        _nonStop.click();
        _price.click();
        amount = driver.findElement(By.xpath("(.//div[@data-ct-handle='solutionPrice'][1])/p")).getText();
        _bookButton.get(0).click();
    }

    public boolean lastStepsOfBooking(String emailId) throws InterruptedException {
        String currentWindow = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        if(handles.size()>1) {
            for (String handle : handles) {
                if (!handle.contains(currentWindow)) {
                    driver.switchTo().window(handle);
                }
            }
        }
        explicitwait(".//input[@value='Continue booking']");
        _continueButton.click();
        explicitwait(".//input[@id='username']");
        _emailAddress.sendKeys(emailId);
        _justContinueButton.click();
        return driver.getCurrentUrl().contains("itinerary");
    }

    public void selectBasedOnOptions(String element,String option) throws InterruptedException {
        Utilities.Select(driver.findElement(By.id(element)),option);
    }

    public void writeToExcel(String file,int sheet, String amount) throws InterruptedException, IOException, InvalidFormatException {
        Utilities.excelWrite(Utilities.dirPath+file,sheet,amount);
        Utilities.htmlReport(Utilities.dirPath+file,sheet);
    }


}
