package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.beust.jcommander.Parameter;
import com.google.protobuf.ByteString.Output;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LogoutPage;
import utilis.Utility;
import utilis.Listener;

@Listeners(utilis.Listener.class)

public class ProjectSpecification extends Utility{
	
	
	@BeforeSuite
	public void setupReport() {
		ExtentSparkReporter reporter = new ExtentSparkReporter("C:\\Users\\madhu\\eclipse-workspace\\Capstone-Project"
				+ "\\reports\\Capstone-Project.html");
		reporter.config().setReportName("Capstone Project Report");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}
	
	@BeforeClass
	public void testDetails() {
		String testName = this.getClass().getSimpleName(); // Use class name as test name
		test = extent.createTest(testName,"Testing" +testDescription);
		test.assignCategory(testCategory);
		test.assignAuthor(testAuthor);
	}
	
	@BeforeMethod
	//@Parameters({"browser", "url"})
	public void launchingBrowserandLoadingURL(){
			//WebDriver driverInstance;
			String browser = Utility.getProperty("browser");
			/*if (browser == null) {
	            browser = Utility.getProperty("browser"); // Read from config file
	        }*/
			//select the browser based on parameter
			if(browser.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("edge")) {
				driver = new EdgeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
			} else {
				driver = new ChromeDriver();
			}
			
		
		// Launch the Application URL from properties file
		driver.get(Utility.getProperty("app.URL"));
		
		driver.manage().window().maximize();
		
		// Set the Implicit Time 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(Utility.getProperty("implicitWait"))));
		
		 // Start Extent Report
       // extent = ExtentReportManager.getInstance();
        
	}
	
	@DataProvider(name = "readData")
	public String[][] readData() throws Exception {
		
		String[][] data = getDataExcel(sheetname);
		return data;
	}
	
	//Get Data from Property Files
	public static void readFromPropFile(String filepath) throws IOException {
        prop = new Properties();
        FileInputStream file = new FileInputStream(filepath);
        prop.load(file);
        file.close();
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
	

	public void WaitUntil(int seconds ) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
	}
	
	@AfterClass
	public void browserquit() {
		 if (driver != null) {
		        driver.quit();
		        driver = null;
		    }

	}
	
    @AfterSuite
    public void reportClose() {
        extent.flush();
    }



}
