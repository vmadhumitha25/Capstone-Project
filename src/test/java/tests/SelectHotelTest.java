package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.ProjectSpecification;
import pages.LoginPage;
import pages.SearchHotelPage;
import pages.SelectHotelPage;

public class SelectHotelTest extends ProjectSpecification {
	
	@BeforeTest
	public void setup() throws IOException {

		testName = "SelectHotel Test";
		testDescription = "Testing the SelectHotel functionality with positive and negative cases";
		testAuthor = "Madhu Mitha";
		testCategory = "Regression Testing";
	}
	
    @Test(priority = 1)
    public void testSelectHotelAndContinue() throws InterruptedException {
    	test = extent.createTest("Select Hotel Test");
    	LoginPage login = new LoginPage(driver);
		login.loginWithPropertyCredentials();
		
		SearchHotelPage search = new SearchHotelPage(driver);
        search.testSearchWithPropertiesFile();
        Thread.sleep(500);
        
    	SelectHotelPage select = new SelectHotelPage(driver);
        select.selectHotelAndContinue();
        Assert.assertTrue(driver.getTitle().contains("Book A Hotel"), "User not navigated to booking page after selecting hotel.");
    }

    @Test(priority = 2)
    public void testContinueWithoutSelection() throws InterruptedException {
    	test = extent.createTest("Select Hotel Test");
    	LoginPage login = new LoginPage(driver);
		login.loginWithPropertyCredentials();
		
		SearchHotelPage search = new SearchHotelPage(driver);
        search.testSearchWithPropertiesFile();
        
    	SelectHotelPage select = new SelectHotelPage(driver);
        select.clickContinueWithoutSelection();
       // Assert.assertEquals(select.errorDisplayed(), "Error message not shown when hotel not selected.");
        System.out.println("Error Displayed :" +select.getErrorMessage());
        Assert.assertEquals(select.getErrorMessage(), "Please Select a Hotel", "Unexpected error message text.");
    }
}
