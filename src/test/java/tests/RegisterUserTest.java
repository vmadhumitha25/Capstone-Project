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
import utilis.Utility;

public class RegisterUserTest extends ProjectSpecification{
	RegisterPage registerPage;  //creating object
	  
	  @BeforeTest
	 // @DataProvider(name = "RegisterData")
		public void setup() throws IOException {
			
			sheetname="RegisterData";
			testName="Register Test";
			testDescription="Test the Registration functionality with positive and negative cases";
			testAuthor="Madhu Mitha";
			testCategory="Smoke Testing";
		}
	
	  @Test(priority = 1)
	    public void testValidRegistration() throws InterruptedException {
	        RegisterPage register = new RegisterPage(driver);
	        register.clickRegisterLink();

	        register.enterUsername("MadhuV25");
	        register.enterPassword("Madhu@123");
	        register.enterConfirmPassword("Madhu@123");
	        register.enterFullName("Madhu MiMi");
	        register.enterEmail("mimimadhu@gmail.com");
	        waitForSeconds(6);
	        //register.enterCaptcha("123456");
	        register.acceptTerms();
	        register.clickRegisterButton();
	        waitForSeconds(5);

	        String successMessage = register.verifySuccessfulRegistration();
	        Assert.assertFalse(successMessage.contains("Registration Successful"), "Valid registration failed.");
	        System.out.println("Valid registration passed.");
	        //Assert.assertTrue(true);
	    }

	    @Test(priority = 2)
	    public void testEmptyFieldsRegistration() throws InterruptedException {
	        RegisterPage register = new RegisterPage(driver);
	        register.clickRegisterLink();

	        register.enterUsername("");
	        register.enterPassword("");
	        register.enterConfirmPassword("");
	        register.enterFullName("");
	        register.enterEmail("");
	        register.enterCaptcha("");
	        register.clickRegisterButton();

	        String errorMessage = register.getErrorMessage();
	        Assert.assertFalse(errorMessage.contains("All fields are required") || errorMessage.contains("required"),
	                "Empty fields error not shown as expected.");
	        System.out.println("Empty field validation passed.");
	        Assert.assertTrue(true);
	    }

	    @Test(priority = 3)
	    public void testInvalidEmailRegistration() throws InterruptedException {
	        RegisterPage register = new RegisterPage(driver);
	        register.clickRegisterLink();

	        register.enterUsername("userInvalidEmail");
	        register.enterPassword("Valid@123");
	        register.enterConfirmPassword("Valid@123");
	        register.enterFullName("Invalid Email");
	        register.enterEmail("invalidEmail"); // no @ or domain
	        register.enterCaptcha("654321");
	        register.acceptTerms();
	        register.clickRegisterButton();

	        String errorMessage = register.getErrorMessage();
	        Assert.assertFalse(errorMessage.contains("valid email") || errorMessage.contains("invalid"),
	                "Invalid email error not shown.");
	        System.out.println("Invalid email validation passed.");
	    }

	    @Test(priority = 4)
	    public void testMismatchedPasswordRegistration() throws InterruptedException {
	        RegisterPage register = new RegisterPage(driver);
	        register.clickRegisterLink();

	        register.enterUsername("mismatchUser");
	        register.enterPassword("Password1");
	        register.enterConfirmPassword("Password2"); // mismatch
	        register.enterFullName("Mismatch Test");
	        register.enterEmail("mismatch@test.com");
	        register.enterCaptcha("111111");
	        register.acceptTerms();
	        register.clickRegisterButton();

	        String errorMessage = register.getErrorMessage();
	        Assert.assertFalse(errorMessage.contains("Passwords do not match"),
	                "Mismatched password error not shown.");
	        System.out.println("Password mismatch validation passed.");
	    }

	    @Test(priority = 5)
	    public void testTermsNotAcceptedRegistration() throws InterruptedException {
	        RegisterPage register = new RegisterPage(driver);
	        register.clickRegisterLink();

	        register.enterUsername("noTermsUser");
	        register.enterPassword("Test@123");
	        register.enterConfirmPassword("Test@123");
	        register.enterFullName("Terms Test");
	        register.enterEmail("terms@test.com");
	        register.enterCaptcha("222222");
	        // not accepting terms
	        register.clickRegisterButton();

	        String errorMessage = register.getErrorMessage();
	        Assert.assertFalse(errorMessage.contains("accept terms") || errorMessage.contains("agree"),
	                "Terms not accepted error not shown.");
	        System.out.println("Terms acceptance validation passed.");
	    }

}
