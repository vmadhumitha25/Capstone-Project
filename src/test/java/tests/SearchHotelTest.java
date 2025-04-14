package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.ProjectSpecification;
import pages.LoginPage;
import pages.SearchHotelPage;
import utilis.Utility;

public class SearchHotelTest extends ProjectSpecification {

	@BeforeTest
	public void setup() throws IOException {

		sheetname = "HotelSearch Test";
		testName = "Signup Test";
		testDescription = "Testing the Sign Up functionality with positive and negative cases";
		testAuthor = "Madhu Mitha";
		testCategory = "Smoke Testing";
	}

	@DataProvider(name = "HotelSearch")
	public Object[][] getSearchData() throws Exception {
		return Utility.getDataExcel("HotelSearch");
	}

	@Test(dataProvider = "HotelSearch")
	public void searchHotelTests(String tcId, String location, String hotel, String roomType, String noOfRooms, String checkIn,
			String checkOut, String adults, String children, String expected) throws InterruptedException, IOException {

		test = extent.createTest("Search Test");
		LoginPage login = new LoginPage(driver);
		login.loginWithPropertyCredentials();

		SearchHotelPage search = new SearchHotelPage(driver);
		search.searchHotel(location, hotel, roomType, noOfRooms, checkIn, checkOut, adults, children);
		search.clickSearchHotel();

		    if (expected.equalsIgnoreCase("valid")) {
		        Assert.assertTrue(driver.getTitle().contains("Select Hotel"), "Valid case failed.");
		    } else {
		        Assert.assertTrue(search.isErrorDisplayed(), "Error message not displayed for invalid input.");
		        String errorMsg = search.getDisplayedErrorMessage();
		        System.out.println("Error displayed: " + errorMsg);
		        screenshot("SearchHotel_Failed_" + tcId);
		        Assert.assertTrue(search.isErrorDisplayed(), "Expected error not displayed for TC: " + tcId);
		    }
	}
}
