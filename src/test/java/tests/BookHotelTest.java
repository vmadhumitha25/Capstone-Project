package tests;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.ProjectSpecification;
import pages.BookHotelPage;
import pages.LoginPage;
import pages.SearchHotelPage;
import pages.SelectHotelPage;
import utilis.Utility;

public class BookHotelTest extends ProjectSpecification {

    @DataProvider(name = "BookHotelDetails")
    public Object[][] getBookingData() throws Exception {
        return Utility.getDataExcel("BookHotelDetails");
    }

    @Test(priority = 1 ,dataProvider = "BookHotelDetails")
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
        Thread.sleep(1000);

       // Capture expected values for later comparison
        String expectedHotel = select.getSelectedHotelName();
        String expectedRoomType = select.getSelectedRoomType();
        String expectedPrice = select.getSelectedPrice();

        select.clickContinue();

        Thread.sleep(1000);
        BookHotelPage book = new BookHotelPage(driver);

   
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);");  
        
        book.fillBookingDetails(fname, lname, addr, cc, type, month, year, cvvNum);
        System.out.println("Booking Details: " + fname + " " + lname + " " + addr + " " + cc + " " + type + " " + month + " " + year + " " + cvvNum);
        System.out.println("Trying to fill First Name field...");
        //firstName.sendKeys(fname);
        book.clickBookNow();
        
        Alert alert = driver.switchTo().alert();
        System.out.println("Alert Text: " + alert.getText());
        alert.dismiss(); 

        // Validation
        if (fname.isEmpty() || lname.isEmpty() || addr.isEmpty() || cc.isEmpty() || cvvNum.isEmpty()) {
            Assert.assertEquals(book.getErrorMessage(), "Expected error message for empty or invalid fields");
        } else {
        	// CONFIRM_01 - Wait for order number (Booking Confirmation)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            String orderNumber = book.getOrderNumber();
            Assert.assertNotNull(orderNumber, "CONFIRM_01 Failed: Order number not generated.");
            System.out.println("CONFIRM_01 Passed: Order Number = " + orderNumber);
            Assert.assertTrue(driver.getPageSource().contains("Booking Confirmation"), "Booking confirmation not found");

            // CONFIRM_02 - Match confirmation data with selected hotel
            String actualHotel = book.getHotelName();
            String actualRoomType = book.getRoomType();
            String actualPrice = book.getTotalPrice();

            Assert.assertEquals(actualHotel, expectedHotel, "Hotel name mismatch!");
            Assert.assertEquals(actualRoomType, expectedRoomType, "Room type mismatch!");
            Assert.assertEquals(actualPrice, expectedPrice, "Price mismatch!");
            System.out.println("CONFIRM_02 Passed: Booking confirmation details matched.");
            
        }
    }

}
