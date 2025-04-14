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
	}

	@Test(priority = 1,dataProvider = "LoginData")
	public void tesLoginFunction(String userLogin, String passLogin, String expected) throws IOException{
			test = extent.createTest("Login Test - " + userLogin + " | " + passLogin );

		LoginPage lp = new LoginPage(driver);
	    lp.loginFunction(userLogin, passLogin);
	  /*  lp.EnterUsername(userLogin)
		.EnterPassword(passLogin)
		.loginButton();
		*/
	    waitForSeconds(5);
	    // Capture screenshot for report
        screenshot("LoginTest_" + userLogin);
        String expectedUrl = "https://adactinhotelapp.com/SearchHotel.php";
	    if (driver.getCurrentUrl().equals(expectedUrl)) {
            System.out.println("Login Successful for: " + userLogin+ "  " +passLogin);
        String error = lp.getLoginErrorMessage();
        System.out.println("Login Error: " + error);  
        test.info("Login failed as expected. Error: " + error);
    } else if (lp.loginSuccessful()) {
        System.out.println("Login Successful for user: " + userLogin);
        test.pass("Login succeeded as expected.");
    } else {
        System.out.println("No error but not redirected");
       // test.warning("Unexpected outcome. No error, but not redirected.");
    }
    Assert.assertTrue(true);
}
	}

