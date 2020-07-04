package POM;

import Utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.net.MalformedURLException;

public class LoginPage extends Utilities {

    public LoginPage() {
        driver = getDriver();
        PageFactory.initElements(driver, this);
    }


    @FindBy(id = "email")
    public WebElement _loginUserName;

    @FindBy(id = "password")
    public WebElement _password;

    @FindBy(xpath = ".//input[@name='date']")
    public WebElement _inputDate;

    @FindBy(xpath = ".//input[@type='submit']")
    public WebElement _submitButton;

    @FindBy(xpath = ".//div/h3[text()='Results']/following-sibling::div")
    public WebElement _verifyDate;

    @FindBy(xpath = ".//div[@class='home_widget']/h1[contains(text(),'RELIANCE CAPITAL LTD')]")
    public WebElement companyProfile;


    public void Login(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(2000);
    }

    public void enterInput(String date) throws InterruptedException {
        _inputDate.sendKeys(date);
        _submitButton.click();
    }

    public String verifyDate() throws InterruptedException {
        return _verifyDate.getText();
    }


}
