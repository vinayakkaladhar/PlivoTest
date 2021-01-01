import POM.QlearlyPage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners(Utilities.ListenerTest.class)

public class QlearlyFeatures extends QlearlyPage {
    public QlearlyPage qlearlyPage;
    boolean result;

    @BeforeMethod
    public void setUp() {
        driver = getDriver();
        driver.get("chrome-extension://aicaflgmmblfaneodjfhkilgplnpjmig/index.html");
        qlearlyPage = new QlearlyPage();
        Reporter.log("Loaded Extension: Qlearly - bookmark manager");

    }

    @Test(description = "Verify the options on the landing page" , priority = 0)
    public void verifyLandingPage() throws InterruptedException, IOException, InvalidFormatException {
        String [] leftPanel = {"Boards","Settings","Help"};
        String [] supportiveLinks = {"Bookmarks","Current tabs","Profile"};
        Reporter.log("On landing page: chrome-extension://aicaflgmmblfaneodjfhkilgplnpjmig/index.html");
        result = qlearlyPage.verifyLandingPage(leftPanel,supportiveLinks);
        Assert.assertTrue(result, "Landing Page Features are displayed");
        Reporter.log("Verified left panel and supportive links existence");
        result = result && qlearlyPage.verifyGuestLogin();
        Assert.assertTrue(result, "Verified default login is: Guest Mode");
        Reporter.log("Verified default login is: Guest Mode");
    }

    @Test(description = "Verify creation of account" , priority = 1)
    public void verifyAccountCreation() throws InterruptedException, IOException, InvalidFormatException {
        qlearlyPage.loginAccount("vinayak@mailinator.com");
        Reporter.log("Logging in with: Vinayak@mailinator.com");
        qlearlyPage.verifyAccount("vinayak@mailinator.com");
        result = qlearlyPage.verifyGuestLogin();
        Assert.assertTrue(!result,"Guest Login is not displayed");
        Reporter.log("Verified login is not in: Guest Mode");
    }

    @Test(description = "Verify board can be shared in public" , priority = 2)
    public void verifyBoardCanBeShared() throws InterruptedException, IOException, InvalidFormatException {
        qlearlyPage.loginAccount("vinayak@mailinator.com");
        qlearlyPage.navigateToBoard("Test");
        Reporter.log("Navigated to Board: Test");
        qlearlyPage.share();
        Reporter.log("Opting for share option for board: Test");
        result = qlearlyPage.verifySharing();
        Assert.assertTrue(result, "Board shared with public with link: "+ publicLink);
        Reporter.log("Board can be accessed publicly from: " +  publicLink);
    }
    @Test(description = "Verify folder creation on qlearly" , priority = 3)
    public void verifyAddingFolder() throws InterruptedException, IOException, InvalidFormatException {
        result = qlearlyPage.addFolder("Folder1",1,1);
        Assert.assertTrue(result, "Folder: Folder1 added successfully");
        Reporter.log("Folder: Folder1 created");
    }

    @Test(description = "Verify multiple folder creation" , priority = 4)
    public void verifyMultipleFoldersCanBeAddedWithDuplicateNames() throws InterruptedException, IOException, InvalidFormatException {
        result = qlearlyPage.addFolder("Folder1",1,1);
        Assert.assertTrue(result, "Folder1: Folder1 added successfully");
        Reporter.log("Folder: Folder1 created");
        result = result && qlearlyPage.addFolder("Folder1",2,2);
        Assert.assertTrue(result, "Folder2:Folder1 added successfully");
        Reporter.log("Folder: Folder2 created");
        Reporter.log("Multiple folders created: Folder count is 2");
    }

   @Test(description = "Verify board creation on qlearly" , priority = 5)
    public void verifyBoardCreation() throws InterruptedException, IOException, InvalidFormatException {
        result = qlearlyPage.addFolder("Folder1",1,1);
        Assert.assertTrue(result, "Folder1 added successfully");
       Reporter.log("Folder: Folder1 created");
        result = result && qlearlyPage.addBoard("Board1",1,1);
        Assert.assertTrue(result, "Board: Board1 added successfully under Folder: Folder1");
        Reporter.log("Board: Board1 added successfully under Folder: Folder1");
    }

    @Test(description = "Verify multiple board creation under different folders" , priority = 6)
    public void verifyMultipleBoardCreation() throws InterruptedException, IOException, InvalidFormatException {
        result = qlearlyPage.addFolder("Folder1",1,1);
        result = result && qlearlyPage.addFolder("Folder1",2,2);
        result = result && qlearlyPage.addBoard("Board1folder1",1,1);
        Reporter.log("Board: Board1 added successfully under Folder: Folder1");
        Reporter.log("Total Board count under Folder: Folder1 is 1");
        result = result && qlearlyPage.addBoard("Board2folder1",1,2);
        Reporter.log("Board: Board2 added successfully under Folder: Folder1");
        Reporter.log("Total Board count under Folder: Folder1 is 2");
        result = result && qlearlyPage.addBoard("Board1folder2",2,1);
        Reporter.log("Board: Board1 added successfully under Folder: Folder2");
        Reporter.log("Total Board count under Folder: Folder2 is 1");
        Assert.assertTrue(result, "Multiple boards created successfully");
        Reporter.log("Multiple boards created successfully");
    }

    @Test(description = "Verify the details displayed on the board" , priority = 7)
    public void verifyEditDuplicateAndDetailsOfBoard() throws InterruptedException, IOException, InvalidFormatException {
        result = qlearlyPage.addFolder("Folder1",1,1);
        result = result && qlearlyPage.addBoard("Board1folder1",1,1);
        result = result && qlearlyPage.verifyBoardDetails("Board1folder1",0);
        Reporter.log("Verified Board: Board1folder1 is created and entry count is 0");
        qlearlyPage.crudOperationBoard();
        Reporter.log("Opted for duplication of Board: Board1folder1");
        result = result && qlearlyPage.addBoard("",1,2);
        Assert.assertTrue(result, "Verified after duplication board count is 2");
        Reporter.log("Verified after duplication board count is 2");
    }

    @Test(description = "Verify column creation on qlearly" , priority = 8)
    public void verifyColumnCreation() throws InterruptedException, IOException, InvalidFormatException {
        result = qlearlyPage.addFolder("Folder1",1,1);
        result = result && qlearlyPage.addBoard("Board1folder1",1,1);
        Reporter.log("Board: Board1folder1 is created");
        qlearlyPage.navigateToBoard("Board1folder1");
        Reporter.log("Navigated to Board: Board1folder1");
        result = result && qlearlyPage.addBookMark("fb link","http://facebook.com", "facebook");
        Reporter.log("Added Bookmark: http://facebook.com");
        Assert.assertTrue(result, "Bookmark: fb link is added");
        Reporter.log("Navigated to Bookmark: http://facebook.com and verified");
        result = result && qlearlyPage.verifyBoardDetails("Board1folder1",1);
        Assert.assertTrue(result, "Verified after adding bookmark - entry count is incremented to 1 for Board:Board1folder1");
        Reporter.log("Verified after adding bookmark - entry count is incremented to 1 for Board:Board1folder1");
    }

    @Test(description = "Verify Multiple bookmarks can be added within a board" , priority = 9)
    public void verifyMultipleBookMarks() throws InterruptedException, IOException, InvalidFormatException {
        result = qlearlyPage.addFolder("Folder1",1,1);
        result = result && qlearlyPage.addBoard("Board1folder1",1,1);
        Reporter.log("Board: Board1folder1 is created");
        qlearlyPage.navigateToBoard("Board1folder1");
        Reporter.log("Navigated to Board: Board1folder1");
        result = result && qlearlyPage.addBookMark("fb link","http://facebook.com", "facebook");
        result = result && qlearlyPage.verifyBoardDetails("Board1folder1",1);
        Reporter.log("Bookmark: http://facebook.com is added");
        qlearlyPage.navigateToBoard("Board1folder1");
        result = result && qlearlyPage.addBookMark("youtube","https://www.youtube.com/", "facebook");
        Reporter.log("Bookmark: https://www.youtube.com/ is added");
        Assert.assertTrue(result, "multiple bookmarks created successfully");
        result = result && qlearlyPage.verifyBoardDetails("Board1folder1",2);
        Assert.assertTrue(result, "Verified after adding bookmark - entry count is incremented to 2 for Board:Board1folder1");
        Reporter.log("Verified after adding multiple bookmarks - entry count is incremented to 2 for Board:Board1folder1");
    }

    @Test(description = "Verify other options in bookmark" , priority = 10)
    public void verifyOtherOptionsInBookMarks() throws InterruptedException, IOException, InvalidFormatException {
        result = qlearlyPage.addFolder("Folder1",1,1);
        result = result && qlearlyPage.addBoard("Board1folder1",1,1);
        qlearlyPage.navigateToBoard("Board1folder1");
        Reporter.log("Navigated to Board: Board1folder1");
        result = result && qlearlyPage.addBookMark("fb link","http://facebook.com", "facebook");
        Reporter.log("Bookmark: http://facebook.com is added");
        result = result && qlearlyPage.verifyBoardDetails("Board1folder1",1);
        qlearlyPage.navigateToBoard("Board1folder1");
        result = result && qlearlyPage.addBookMark("youtube","https://www.youtube.com/", "facebook");
        Reporter.log("Bookmark: https://www.youtube.com/ is added");
        result = result && qlearlyPage.verifyBoardDetails("Board1folder1",2);
        qlearlyPage.navigateToBoard("Board1folder1");
        result = result && qlearlyPage.addBookMark("fb link","http://facebook.com", "facebook");
        Reporter.log("Duplicate Bookmark: http://facebook.com is added");
        result = result && qlearlyPage.verifyBoardDetails("Board1folder1",3);
        Reporter.log("Entry count with duplicates is displayed as 3 for Board: Board1folder1");
        qlearlyPage.navigateToBoard("Board1folder1");
        qlearlyPage.crudOperationColumns();
        Reporter.log("Performing remove duplicate of bookmarks for Board: Board1folder1");
        result = result && qlearlyPage.verifyBoardDetails("Board1folder1",2);
        Assert.assertTrue(result, "Verified after removing duplicate bookmark - entry count is decremented to 2 for Board:Board1folder1");
        Reporter.log("Verified after removing duplicate bookmark - entry count is decremented to 2 for Board:Board1folder1");
    }

    @AfterMethod
    public void closeDriver() {
        if (driver!= null){
            driver.close();
            driver.quit();
            driver = null;
        }


    }
}