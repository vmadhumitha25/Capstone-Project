package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.github.dockerjava.transport.DockerHttpClient.Request.Method;

import base.ProjectSpecification;
import pages.LoginPage;
import utilis.ExtentReportManager;
import utilis.Utility;

public class LoginTest extends ProjectSpecification {
	
	@BeforeTest
	public void setup() throws IOException {
		
		sheetname="LoginData";
		testName="Login Test";
		testDescription="Testing the login functionality with positive and negative cases";
		testAuthor="Madhu Mitha";
		testCategory="Smoke Testing";
	}
	
	@DataProvider(name = "LoginData")
	public Object[][] LoginData() throws Exception {
	    return Utility.getDataExcel("LoginData");
	    //return Utility.getDataExcel("C:\Users\madhu\eclipse-workspace\Capstone-Project\src\test\resources\
	    //Capstone-Project.xlsx", "LoginData");
	}

	@Test(priority = 1,dataProvider = "LoginData")
	public void tesLoginFunction(String userLogin, String passLogin, String expected) throws IOException{
		try {
			//test = extent.createTest("Login Test - " + userLogin + " | " + browserName );

		LoginPage lp = new LoginPage(driver);
	    lp.loginFunction(userLogin, passLogin);
	    lp.EnterUsername(userLogin)
		.EnterPassword(passLogin)
		.loginButton();
		//Searchhotelpage objSearchhotelpage = new Searchhotelpage(driver);
		//objSearchhotelpage.userverifyID();
	    if (expected.equalsIgnoreCase("valid")) {
            Assert.assertTrue(driver.getCurrentUrl().contains("SearchHotel"));
        } else if (expected.equalsIgnoreCase("invalid")) {
            Assert.assertTrue(lp.errorMessageDisplayed());
        }
		}
		catch (Exception e) {
            screenshot("LoginTest_Failure_" + userLogin);
		}
        screenshot("loginTest_" + userLogin);
   
        Assert.fail("Test failed due to exception.");
    
	    Assert.assertTrue(driver.getTitle().contains("Search Hotel"), "Login failed: Page title mismatch.");
	    //lp.logoutButton(); // Validate logout works after login
	    
        String expectedUrl = "  ";  
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed for valid credentials");
        Assert.assertEquals("AdactIn.com - Search Hotel", driver.getTitle());  
        
        System.out.println("No more data in Excel. Stopping test.");
        System.exit(0); 	
        System.out.println("Logging in with: " + userLogin + " | " + passLogin);
    }
}

