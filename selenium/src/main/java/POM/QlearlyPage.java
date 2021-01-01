package POM;

import Utilities.Utilities;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QlearlyPage extends Utilities {

    public QlearlyPage() {
        driver = getDriver();
        PageFactory.initElements(driver, this);
    }

    public static String amount;
    public static String publicLink;


    @FindBy(id = "reg_name")
    public WebElement _name;

    @FindBy(xpath = ".//form[@id='signup_form']//button[text()='Sign In']")
    public WebElement _signIn;

    @FindBy(xpath = ".//form[@id='login_form']//button[text()='Sign In']")
    public WebElement _login;

    @FindBy(id = "email")
    public WebElement _email;

    @FindBy(id = "password")
    public WebElement _password;

    @FindBy(id = "register_btn")
    public WebElement _register;

    @FindBy(id = "acc_username")
    public WebElement _username;

    @FindBy(xpath = "//a[text()='Duplicate']")
    public WebElement _duplicateOption;

    @FindBy(xpath = ".//a[text()=' Remove Duplicate']")
    public WebElement _removeDuplicate;

    @FindBy(xpath = ".//a[text()=' Share']")
    public WebElement _shareLink;

    @FindBy(xpath = ".//span[text()='Profile']")
    public WebElement _profileButton;

    @FindBy(xpath = ".//button[contains(text(),'Login')]")
    public WebElement _signUp;

    @FindBy(xpath = "//div[@class='dotted_menu']")
    public WebElement _crudoperation;

    @FindBy(id = "guestmode")
    public WebElement _guestMode;

    @FindBy(xpath = ".//input[@value='Continue']")
    public WebElement _justContinueButton;

    @FindBy(xpath = "//button[text()='Open']")
    public WebElement _openButton;

    @FindBy(xpath = "//button[contains(text(),'CREATE')]")
    public WebElement _createBookMark;

    @FindBy(id = "entry_website_url")
    public WebElement _url;

    @FindBy(id = "entry_title")
    public WebElement _title;

    @FindBy(xpath = "//button[text()='Add']")
    public WebElement _addBookMark;

    @FindBy(xpath = "//div[contains(text(),'Create Column')]")
    public WebElement _createColumn;

    @FindBy(xpath = "//button[text()='Create']")
    public WebElement _createButton;

    @FindBy(xpath = "//input[@name='boardname']")
    public WebElement _boardName;

    @FindBy(xpath = "//div[@class='row board-row ']")
    public List<WebElement> folders;

    @FindBy(xpath = "//h4[text()='Add Folder']")
    public WebElement _addFolder;

    @FindBy(xpath = "//button[contains(text(),'Create Link')]")
    public WebElement _createSharingLink;

    @FindBy(xpath = "//h6/a")
    public WebElement _clickSharingLink;


    public boolean verifyLandingPage(String[] leftPanel, String[] supportiveLinks) throws InterruptedException {
        boolean optionsFound = true;
        for(int i=0;i<leftPanel.length;i++){
            optionsFound = optionsFound && driver.findElement(By.xpath(String.format(".//h4[text()='%s']",leftPanel[i]))).isDisplayed();
        }
        for(int i=0;i<supportiveLinks.length;i++){
            optionsFound = optionsFound && driver.findElement(By.xpath(String.format(".//li/a/span[text()='%s']",supportiveLinks[i]))).isDisplayed();
        }
        return optionsFound;
    }

    public boolean verifyGuestLogin() throws InterruptedException {
        try{
            return _guestMode.isDisplayed();
        }catch(Exception e){
            return false;
        }

    }

    public boolean addFolder(String folderName, int rowNumber, int rowCount) throws InterruptedException {
        _addFolder.click();
        driver.findElement(By.xpath(String.format("//div[@class='row board-row '][%d]//input",rowNumber))).click();
        driver.findElement(By.xpath(String.format("//div[@class='row board-row '][%d]//input",rowNumber))).sendKeys(folderName);
        return folders.size() == rowCount;
    }

    public boolean addBoard(String boardName, int folder, int boardCount) throws InterruptedException {
        if(boardName != ""){
            driver.findElement(By.xpath(String.format("//div[@class='row board-row '][%d]//div/a[@class='link-hover']",folder))).click();
            _boardName.sendKeys(boardName);
            _createButton.click();
        }
        List boards = driver.findElements(By.xpath(String.format("//div[@class='row board-row '][%d]//div/a[@href='board-detail.html']",folder)));
        return boards.size() == boardCount;
    }

    public boolean verifyBoardDetails(String boardName, int entryCount) throws InterruptedException {
        driver.get("chrome-extension://aicaflgmmblfaneodjfhkilgplnpjmig/index.html");
        return driver.findElement(By.xpath(String.format("//div[@class='row board-row '][1]//div[@class='media']//a[text()='%s']//parent::h4//following-sibling::span[contains(text(),'%s ')]",
                boardName,entryCount)))
                .isDisplayed();
    }

    public void crudOperationBoard () throws InterruptedException {
        _crudoperation.click();
        _duplicateOption.click();
    }

    public void crudOperationColumns () throws InterruptedException {
        _crudoperation.click();
        _removeDuplicate.click();
        _createColumn.click();
    }

    public void share() throws InterruptedException {
        _crudoperation.click();
        _shareLink.click();
    }


    public void navigateToBoard(String boardName) throws InterruptedException {
        Thread.sleep(2000);
        WebElement ele =  driver.findElement(By.xpath(String.format("//div[@class='row board-row '][1]//div[@class='media']//a[text()='%s']", boardName)));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", ele);
        Utilities.explicitwait("//div[contains(text(),'Create Column')]");
    }

    public boolean addBookMark(String name,String url, String toassert) throws InterruptedException {
        _addBookMark.click();
        Utilities.explicitwait(".//h4[contains(text(),'Create New')]");
        _title.sendKeys(name);
        _url.sendKeys(url);
        _createBookMark.click();
        _openButton.click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size()-1));
        Thread.sleep(3000);
        return driver.getCurrentUrl().contains(toassert);
    }

    public void loginAccount(String email) throws InterruptedException {
        _profileButton.click();
        Thread.sleep(2000);
        _signUp.click();
        Thread.sleep(2000);
        _email.sendKeys(email);
        _password.sendKeys("Rajini@123");
        _login.click();
        Thread.sleep(10000);

    }

    public void verifyAccount(String email) throws InterruptedException {
        driver.get("chrome-extension://aicaflgmmblfaneodjfhkilgplnpjmig/index.html");
        _profileButton.click();
        _username.getText().contains(email);
    }

    public boolean verifySharing() throws InterruptedException {
        Thread.sleep(2000);
        _clickSharingLink.click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size()-1));
        Utilities.explicitwait(".//p");
        publicLink = driver.findElement(By.xpath(".//p")).getText();
        return (publicLink.contains("This column has been shared publicly."));
    }

}
