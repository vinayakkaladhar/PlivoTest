package Utilities;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext ;
import org.testng.ITestListener ;
import org.testng.ITestResult ;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import sun.util.calendar.BaseCalendar;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListenerTest implements ITestListener
    {
        public WebDriver driver;

        @AfterSuite
        public void onFinish(ITestContext arg0) {
            // TODO Auto-generated method stub
           driver =  Utilities.getDriver();
           driver.close();

        }

        @BeforeSuite
        public void onStart(ITestContext arg0) {
                System.out.println("******" + arg0.getName() + "*********");
            // TODO Auto-generated method stub

        }

        @Override
        public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTestFailure(ITestResult arg0) {
            try{

                takeScreenShot(Utilities.getDriver(),arg0.getName());
                // TODO Auto-generated method stub
            }
          catch(Exception e){
              e.printStackTrace();
          }
            finally {
//                driver.close();
            }

        }

        public void takeScreenShot(WebDriver driver,String methodName) {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            //The below method will save the screen shot in d drive with test method name
            try {
                FileUtils.copyFile(scrFile, new File("./"+dateobj+methodName+".png"));
                System.out.println("***Placed screen shot in "+"./"+" ***");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTestSkipped(ITestResult arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTestStart(ITestResult arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTestSuccess(ITestResult arg0) {
            // TODO Auto-generated method stub

        }
    }

