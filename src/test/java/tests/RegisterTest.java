package tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.ProjectSpecification;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.RegisterPage;
import utilis.ConfigReader;
import utilis.Utility;

public class RegisterTest extends ProjectSpecification{
	RegisterPage registerPage;  //creating object
	  
	  @BeforeTest
	 // @DataProvider(name = "RegisterData")
		public void setup() throws IOException {
			
			sheetname="RegisterData";
			testName="Login Test";
			testDescription="Test the Registration functionality with positive and negative cases";
			testAuthor="Madhu Mitha";
			testCategory="Smoke Testing";
		}
	
	@Test(priority = 1, dataProvider = "RegisterData") 
	public void testRegistration(String tcId, String username, String password, String confirmPassword, String Fullname, 
			String email, String captcha, String termsAccepted, String expected) {
		registerPage = new RegisterPage(driver); 
		registerPage.clickRegisterLink();
		registerPage.enterUsername(username);
		registerPage.enterPassword(password);
		registerPage.enterConfirmPassword(confirmPassword);
		registerPage.enterFullName(Fullname);
		registerPage.enterEmail(email);
		registerPage.enterCaptcha(captcha);
		if (termsAccepted.equalsIgnoreCase("yes")) {
			registerPage.acceptTerms();
		}
		registerPage.clickRegisterButton();
	
	if (expected.equalsIgnoreCase("success")) {
		registerPage.verifySuccessfulRegistration();
    } else {
    	registerPage.verifyInvalidRegistration(tcId);  
    }
	
	}
	
	@Test (priority = 2)
	public void testResetFunctionality() {
		registerPage.clickRegisterLink();
		registerPage.enterUsername(ConfigReader.getProperty("username"));
		registerPage.enterPassword(ConfigReader.getProperty("password"));
		registerPage.enterConfirmPassword(ConfigReader.getProperty("confirmPassword"));
		registerPage.enterFullName(ConfigReader.getProperty("fullName"));
		registerPage.enterEmail(ConfigReader.getProperty("email"));
		registerPage.enterCaptcha(ConfigReader.getProperty("captcha"));
		registerPage.acceptTerms();

		registerPage.clickResetButton();
		registerPage.verifyFormReset();
	}
	
    public void clicks_on_the_reset_button() {
		registerPage.clickResetButton();
    }

    public void all_form_fields_should_be_cleared() {
    	registerPage.verifyFormReset();
    }
    
	@AfterMethod
    public void refreshPage() {
        driver.navigate().refresh();
    }

}
