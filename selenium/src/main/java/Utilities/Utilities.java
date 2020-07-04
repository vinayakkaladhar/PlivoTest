package Utilities;

import com.gembox.spreadsheet.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
import java.util.List;

public class Utilities {
    public static WebDriver driver;
    public static String dirPath;

    public static WebDriver getDriver(){
        dirPath = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver",dirPath+"/src/main/resources/chromedriver");
        if(driver == null){
            ChromeOptions chromeOptions = new ChromeOptions();
            try {
                //driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
                //driver.manage().window().maximize();
                driver = new ChromeDriver();
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

    public static void excelWrite(String path,int sheet,String amount) throws IOException {
        File src=new File(path);
        FileInputStream fis=new FileInputStream(src);
        XSSFWorkbook wb=new XSSFWorkbook(fis);
        XSSFSheet sh = wb.getSheetAt(sheet);
        sh.getRow(0).getCell(1).setCellValue(amount);
        FileOutputStream fos=new FileOutputStream(new File(path));
        wb.write(fos);
        fos.close();
    }

        public static void explicitwait(String element){
        WebDriverWait wait = new WebDriverWait(driver,25);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));

    }

    public static void htmlReport(String path,int index) throws IOException {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");

        ExcelFile workbook = ExcelFile.load(path);

        ExcelWorksheet worksheet = workbook.getWorksheet(index);

        // Some of the properties from ExcelPrintOptions class are supported in HTML export.
        worksheet.getPrintOptions().setPrintHeadings(true);
        worksheet.getPrintOptions().setPrintGridlines(true);

        // Print area can be used to specify custom cell range which should be exported to HTML.
        worksheet.getNamedRanges().setPrintArea(worksheet.getCells().getSubrange("A1", "J42"));

        HtmlSaveOptions options = new HtmlSaveOptions();
        options.setHtmlType(HtmlType.HTML);
        options.setSelectionType(SelectionType.ENTIRE_FILE);

        workbook.save(dirPath+ "/src/main/resources/HybdExpenses.html", options);
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
