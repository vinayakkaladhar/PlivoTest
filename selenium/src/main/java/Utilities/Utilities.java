package Utilities;

import com.gembox.spreadsheet.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Utilities {
    public static WebDriver driver;
    public static String dirPath;
    public static WebDriver getDriver(){
            dirPath = System.getProperty("user.dir");
            System.setProperty("webdriver.chrome.driver", dirPath + "/src/main/resources/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addExtensions(new File(dirPath + "/src/main/resources/extension_3_0_3_0.crx"));
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        if(driver == null) {
            try {
                driver = new ChromeDriver(capabilities);
                driver.manage().window().maximize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return driver;
    }

    public static void takeSnapShot(WebDriver driver,String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot

        Screenshot screenshot = new AShot().takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "jpg", new File(fileWithPath));

    }

    public static void explicitwait(String element){
        WebDriverWait wait = new WebDriverWait(driver,25);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));

    }

    public static void excelTOPDF(String[] args) {
    }

    public static void Select(WebElement element, String option){
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
  for(int i=0;i<options.size();i++){
      if(options.get(i).getText().equals(option)){
          options.get(i).click();
      }

        }

    }


}
