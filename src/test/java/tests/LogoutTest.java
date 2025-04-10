package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.ProjectSpecification;
import pages.BookHotelPage;
import pages.LoginPage;
import pages.LogoutPage;
import pages.SearchHotelPage;
import pages.SelectHotelPage;
import utilis.ConfigReader;
import utilis.Utility;

public class LogoutTest extends ProjectSpecification{
	LoginPage loginPage;
	LogoutPage logoutPage;

	@BeforeMethod
    public void loginBeforeTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginFunction("madhu2mtha@gmail.com", "Madhu@123");
    }
	
	@Test(priority =1)
	public void logoutBtnVisible() {
		loginPage.loginFunction("madhu2mtha@gmail.com", "Madhu@123");
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
        select.clickContinue();
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
        Assert.assertTrue(driver.getCurrentUrl().contains("Login"), "Did not navigate back to login page.");
    
		Assert.assertTrue(logoutPage.logoutBtnDisplayed(), "User is not redirect to home page after logout");
		WebElement linkText = driver.findElement(By.className("reg_success"));
        Assert.assertEquals("You have successfully logged out. Click here to login again", linkText.getText());
        
        driver.navigate().to("https://adactinhotelapp.com/BookingConfirm.php");
        Assert.assertTrue(logoutPage.loginPageDisplayed());
	}
	
	@Test(priority = 3)
	// Verify if redirected to login page
    public boolean LoginPageDisplayed() {
        return Utility.driver.getCurrentUrl().contains("index.php");
    }
	
	@Test (priority = 4)
    public void testDirectLogoutWithoutLogin() {
        driver.navigate().to(ConfigReader.getProperty("LogoutURL"));
        Assert.assertTrue(driver.getCurrentUrl().contains("index"));
    }
	
	@Test(priority = 5)
	public void refreshAfterLogout() {
		
        loginPage.loginFunction("madhu2mtha@gmail.com", "Madhu@123");
        logoutPage.clickLogoutBtn();
   
        driver.navigate().refresh();
        Assert.assertTrue(driver.getCurrentUrl().contains(ConfigReader.getProperty("loginURL")));
    }
	
	@Test (priority = 6)
    public void testLogoutInMultipleTabs() throws Exception {
       
		loginPage.loginWithPropertyCredentials();
        String url = driver.getCurrentUrl();
        logoutPage.clickLogoutBtn();
        driver.navigate().to(url);
        Assert.assertTrue(logoutPage.loginPageDisplayed());
    }
	
	 @Test (priority = 7)
	    public void testLogoutWithInvalidSession() throws Exception {
	      
	        loginPage.loginFunction("mimithra", "mithra@123");
	        Assert.assertTrue(driver.getCurrentUrl().contains("index"));
	    }
}
