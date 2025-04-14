package tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.ProjectSpecification;
import pages.BookHotelPage;
import pages.LoginPage;
import pages.SearchHotelPage;
import pages.SelectHotelPage;
import utilis.Utility;

public class BookHotelTest extends ProjectSpecification {

	@BeforeTest
	public void setup() throws IOException {
		
		sheetname="BookHotelDetails";
		testName="Book Hotel Test";
		testDescription="Testing the Book Hotel functionality with positive and negative cases";
		testAuthor="Madhu Mitha";
		testCategory="Smoke Testing";
	}
	
    @DataProvider(name = "BookHotelDetails")
    public Object[][] getBookingData() throws Exception {
        return Utility.getDataExcel("BookHotelDetails");
    }

    @DataProvider(name= "bookingData")
    public Object[][]bookingData(){
    	return new Object[][] {
    		{"Mokthi", "Doe", "123 Street", "4111111111111111", "VISA", "March", "2026", "123"},
    		{"  ", "Magesh", "123 Street", "542", "VISA", "March", "2026", "123"},
    		{"Divya", "Magesh", "123 Street", "4111111111111111", "VISA", "Test", "2026", "123"},
            {"Mimi", " ", "123 Street", "4111111111111111", "VISA", "March", "2023", "123"},
            {"@#$%^&", "Princy", "123 Street", "4111111111111111", "VISA", "March", "2026", "xxx"}
    	};
    }
    @Test(priority = 1 ,dataProvider = "bookingData")
   // @Test(priority = 1 ,dataProvider = "BookHotelDetails")
    public void bookHotelTests(String fname, String lname, String addr, String cc, String type, String month,
			String year, String cvvNum) throws InterruptedException {

    	// Step : 1 Login to Application
    	LoginPage login = new LoginPage(driver);
		login.loginWithPropertyCredentials();
		
		// Step 2 : Search for Hotel
		SearchHotelPage search = new SearchHotelPage(driver);
        search.testSearchWithPropertiesFile();
        Thread.sleep(500);
        
        // Step 3 : Select an Hotel
    	SelectHotelPage select = new SelectHotelPage(driver);
        select.selectHotelAndContinue();
        Thread.sleep(5000);
     
        // Capture the hotel details before proceeding with the booking
   
        /*WebElement selectedHotelNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".selected-hotel-name")));
        String selectedHotelName = selectedHotelNameElement.getText();
        System.out.println("Hotel Name: " + selectedHotelName);

        String selectedRoomType = select.getSelectedRoomType();
        System.out.println("Hotel :" +selectedRoomType);
       
        String selectedHotelLocation = select.getSelectedHotelLocation();
        System.out.println("Hotel :" +selectedHotelLocation);
        
        String selectedPrice = select.getSelectedPrice();
        System.out.println("Hotel :" +selectedPrice);
        */
     // Step 4: Book Hotel
        BookHotelPage book = new BookHotelPage(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);");
        
       // book.fillBookingDetails("Madhu", "Mitha", "123 Street", "4111111111111111", "VISA", "March", "2026", "123");
        book.fillBookingDetails(fname, lname, addr, cc, type, month, year, cvvNum);
        book.clickBookNow();
        Thread.sleep(3000); // Wait for booking to confirm
        
        String errorMsg = book.getErrorMessage();
        System.out.println("Error: " + errorMsg);
        Assert.assertTrue(errorMsg.contains("Please"), "Expected error for missing fields");
        String orderId = book.getOrderNumber();
        System.out.println("Order ID from booking: " + orderId);
        Assert.assertTrue(true);

        
     // Handle the alert (if any)
        Alert alert = driver.switchTo().alert();
        System.out.println("Alert Text: " + alert.getText());
        alert.dismiss();

        //Validation
       /* if (expectedResult.equalsIgnoreCase("Valid")) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            String orderIdBooking = book.getOrderNumber();
            Assert.assertNotNull(orderId, "Order ID was not generated.");
            System.out.println("Booking successful. Order ID: " + orderIdBooking);

            // Validate hotel details
            /*Assert.assertEquals(book.getHotelName(), selectedHotelName, "Hotel name mismatch");
            Assert.assertEquals(book.getRoomType(), selectedRoomType, "Room type mismatch");
            Assert.assertEquals(book.getHotelLocation(), selectedLocation, "Location mismatch");
            Assert.assertEquals(book.getTotalPrice(), selectedPrice, "Price mismatch");

            System.out.println("All booking details match the selection.");
        } else {
        	// CONFIRM_01 - Wait for order number (Booking Confirmation)
        	String errorMessage = book.getErrorMessage();
            Assert.assertTrue(errorMessage.contains("Please") || errorMessage.length() > 0,
                    "Expected an error message but none found.");
            System.out.println("Booking failed as expected. Error: " + errorMessage);
        }
        
            // CONFIRM_02 - Match confirmation data with selected hotel
         // Capture the confirmation details
            String confirmedHotelName = book.getHotelName();
            String confirmedRoomType = book.getRoomType();
            String confirmedHotelLocation = book.getHotelLocation();
            String confirmedPrice = book.getTotalPrice();

             /*Assertions to verify that the details match
            Assert.assertEquals(confirmedHotelName, selectedHotelName, "Hotel name does not match!");
            Assert.assertEquals(confirmedRoomType, selectedRoomType, "Room type does not match!");
            Assert.assertEquals(confirmedHotelLocation, selectedHotelLocation, "Hotel location does not match!");
            Assert.assertEquals(confirmedPrice, selectedPrice, "Price does not match!");
            */
        
    }

    @Test(priority = 2)
    public void testCancelButtonFunctionality() throws InterruptedException {
    	
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
        book.fillBookingDetails("Madhu", "Mitha", "123 Street", "4111111111111111", "VISA", "March", "2026", "123");
        book.clickCancel();
        waitForSeconds(4);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("SearchHotel") || currentUrl.contains("SelectHotel"),
                "Cancel button did not navigate as expected.");

        System.out.println("Cancel button redirected to correct page: " + currentUrl);
    }

}
