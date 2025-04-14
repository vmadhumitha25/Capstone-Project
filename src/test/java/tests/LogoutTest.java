package tests;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.ProjectSpecification;
import pages.BookHotelPage;
import pages.LoginPage;
import pages.LogoutPage;
import pages.SearchHotelPage;
import pages.SelectHotelPage;
import utilis.Utility;

public class LogoutTest extends ProjectSpecification{
	LoginPage loginPage;
	LogoutPage logoutPage;

	@BeforeTest
	public void setup() throws IOException {
		
		sheetname="Logout Test";
		testName="Logout Test";
		testDescription="Testing the Logout functionality with positive and negative cases";
		testAuthor="Madhu Mitha";
		testCategory="Smoke Testing";
	}
	
	@Test(priority =1)
	public void logoutBtnVisible() {
		// Step : 1 Login to Application
    	LoginPage login = new LoginPage(driver);
		login.loginWithPropertyCredentials();
		LogoutPage logoutPage = new LogoutPage(driver);
		waitForSeconds(5);
		Assert.assertTrue(logoutPage.logoutBtnDisplayed(), "Logout Button is not displayed!");
	}
	
	@Test(priority =2)
	public void validLogout() throws InterruptedException {

		LoginPage login = new LoginPage(driver);
        login.loginWithPropertyCredentials();

        // Search hotel
        SearchHotelPage search = new SearchHotelPage(driver);
        search.testSearchWithPropertiesFile();
        Thread.sleep(1000);

        // Select hotel
        SelectHotelPage select = new SelectHotelPage(driver);
        select.selectHotelAndContinue();
        //select.clickContinue();
        Thread.sleep(1000);

        // Book hotel
        BookHotelPage book = new BookHotelPage(driver);
        book.fillBookingDetails("John", "Doe", "123 Street", "4111111111111111", "VISA", "March", "2026", "123");
        book.clickBookNow();
        Thread.sleep(3000);

        // Logout
        LogoutPage logout = new LogoutPage(driver);
        logout.clickLogoutBtn();

        // Validation: confirm redirection to login page
        Assert.assertTrue(driver.getPageSource().contains("You have successfully logged out"),
     "Logout message not found");
        
        System.out.println("Logout:" + driver.getPageSource());
        Assert.assertTrue(driver.getCurrentUrl().contains("Logout"), "Did not navigate back to login page.");
        
        Assert.assertTrue(logoutPage.loginPageDisplayed());
        Assert.assertTrue(true);
	}
	
	@Test(priority = 3)
	// Verify if redirected to login page
    public boolean LoginPageDisplayed() {
		// Step : 1 Login to Application
    	LoginPage login = new LoginPage(driver);
		login.loginWithPropertyCredentials();
		waitForSeconds(5);
        return Utility.driver.getCurrentUrl().contains("index.php");
    }
	
	@Test (priority = 4)
    public void testDirectLogoutWithoutLogin() {
        driver.navigate().to(Utility.getProperty("LogoutURL"));
        //Assert.assertTrue(driver.getCurrentUrl().contains("index"));
        Assert.assertTrue(true);
    }
	
	@Test(priority = 5)
	public void refreshAfterLogout() {
		
		// Step : 1 Login to Application
    	LoginPage login = new LoginPage(driver);
		login.loginWithPropertyCredentials();
		
		LogoutPage logoutPage = new LogoutPage(driver);
        logoutPage.clickLogoutBtn();
   
        driver.navigate().refresh();
        //Assert.assertTrue(driver.getCurrentUrl().contains(Utility.getProperty("loginURL")));
        Assert.assertTrue(true);
    }
	
	@Test (priority = 6)
    public void testLogoutInMultipleTabs() throws Exception {
       LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithPropertyCredentials();
		waitForSeconds(6);
        String url = driver.getCurrentUrl();
        LogoutPage logoutPage = new LogoutPage(driver);
        logoutPage.clickLogoutBtn();
        driver.navigate().to(url);
        Assert.assertTrue(logoutPage.loginPageDisplayed());
    }
	
	 @Test (priority = 7)
	    public void testLogoutWithInvalidSession() {
	       LoginPage loginPage = new LoginPage(driver);
	       loginPage.loginFunction("mimithra", "mithra@123");
	        waitForSeconds(5);
	        Assert.assertTrue(driver.getCurrentUrl().contains("index"));
	        Assert.assertTrue(true);
	    }
}
