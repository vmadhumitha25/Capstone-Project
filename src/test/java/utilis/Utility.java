package utilis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Utility {
	public static Properties prop;
	public static String filepath;
	public String sheetname;

	public static WebDriver driver; // remove static keyword for parallel execution
	public static ExtentReports extent;
	public static ExtentTest test;
	public static Object waitForVisibility;
	public String browserName;
	public String testName, testDescription, testCategory, testAuthor;
	public static final int TIMEOUT = 10;


	public static String[][] getDataExcel(String sheetname) throws Exception {
		XSSFWorkbook book = new XSSFWorkbook(
				"C:\\Users\\madhu\\eclipse-workspace\\Capstone-Project\\src\\test\\resources\\Capstone-Project.xlsx");
		XSSFSheet sheet = book.getSheet(sheetname);
		int rowCount = sheet.getLastRowNum();
		System.out.println("Row count: "+rowCount);
		int columnCount = sheet.getRow(0).getLastCellNum();
		System.out.println("Column count: "+columnCount);
		
		String[][] data = new String[rowCount][columnCount];
	    DataFormatter formatter = new DataFormatter(); 
	    
		
		for(int i =1 ; i <= rowCount; i++) {
			XSSFRow row = sheet.getRow(i);
			for(int j =0 ; j<columnCount; j++) {
				XSSFCell cell = row.getCell(j);
				//data[i-1][j] = cell.getStringCellValue();
				data[i - 1][j] = formatter.formatCellValue(cell);
			}
		}
        book.close();
		return data;
	}

	public static void readFromPropFile(String filepath) throws IOException {

		FileReader file = new FileReader(filepath);
		prop = new Properties();
		prop.load(file);
	}

	public static String screenshot(String name) throws IOException {

		String path = "C:\\Users\\madhu\\eclipse-workspace\\Capstone-Project\\screenshots" + name+ ".png";
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = new File(path);
		FileUtils.copyFile(src, dest);
		return path;
	}
	
	    // Explicit wait
	    public static void waitForVisibility(WebDriver driver, WebElement element) {
	        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT)).until(ExpectedConditions.visibilityOf(element));
	    }

	@AfterMethod
	//Capture the screenshot if the test fails
	public void screenshot(ITestResult result) {
	    if (ITestResult.FAILURE == result.getStatus()) {
	        try {
	        	String path="C:\\Users\\madhu\\eclipse-workspace\\Capstone-Project\\screenshots";
	            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	            String screenshotName = "screenshots/" + result.getName() + ".png";
	            FileUtils.copyFile(srcFile, new File(screenshotName));
	            System.out.println("Screenshot taken for failed test: " + result.getName());
	        } catch (Exception e) {
	            System.out.println("Exception while taking screenshot: " + e.getMessage());
	        }
	    }
	}
	
	
}
