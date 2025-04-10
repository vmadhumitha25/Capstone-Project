package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.ProjectSpecification;
import pages.LoginPage;
import pages.SearchHotelPage;
import utilis.Utility;

public class SearchHotelTest extends ProjectSpecification {

	@DataProvider(name = "HotelSearch")
	public Object[][] getSearchData() throws Exception {
		return Utility.getDataExcel("HotelSearch");
	}

	//@BeforeClass
	@Test(dataProvider = "HotelSearch")
	public void searchHotelTests(String location, String hotel, String roomType, String noOfRooms, String checkIn,
			String checkOut, String adults, String children, String expected) throws InterruptedException {

		LoginPage login = new LoginPage(driver);
		login.loginWithPropertyCredentials();

		SearchHotelPage search = new SearchHotelPage(driver);
		search.searchHotel(location, hotel, roomType, noOfRooms, checkIn, checkOut, adults, children);
		search.clickSearchHotel();

		switch (expected) {
		case "Success":
			Assert.assertTrue(driver.getTitle().contains("Select Hotel"));
			break;
		case "CheckOutBeforeCheckInError":
			Assert.assertTrue(driver.getPageSource().contains("Check-Out Date shall be after than Check-In Date"));
			break;
		case "PastDatesError":
			Assert.assertTrue(driver.getPageSource().contains("Check-In Date shall be later than today's date"));
			break;
		case "DataVerification":
			Assert.assertTrue(driver.getPageSource().contains(hotel));
			break;
		case "PriceVerification":
			Assert.assertTrue(driver.getPageSource().contains("$")); // Replace with actual price check
			break;
		default:
			Assert.fail("Invalid expected result type");
		}
	}
}
