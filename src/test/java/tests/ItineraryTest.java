package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.ProjectSpecification;
import pages.BookHotelPage;
import pages.ItineraryPage;
import pages.LoginPage;
import pages.SearchHotelPage;
import pages.SelectHotelPage;

public class ItineraryTest extends ProjectSpecification{

	String orderId;

    @Test(priority = 1)
    public void confirmBookingAndFetchOrderId() throws InterruptedException {
        // Login and Book
        LoginPage login = new LoginPage(driver);
        login.loginWithPropertyCredentials();

        SearchHotelPage search = new SearchHotelPage(driver);
        search.testSearchWithPropertiesFile();

        SelectHotelPage select = new SelectHotelPage(driver);
        select.selectHotelAndContinue();
        select.clickContinue();

        BookHotelPage book = new BookHotelPage(driver);
        book.fillBookingDetails("John", "Doe", "123 Main St", "1234567812345678",
                "VISA", "March", "2026", "123");

        Thread.sleep(3000); // Wait for booking to confirm
        orderId = book.getOrderNumber();
        System.out.println("Order ID from booking: " + orderId);

        Assert.assertNotNull(orderId, "Booking failed: Order ID is null.");
        Assert.assertTrue(driver.getPageSource().contains("Booking Confirmation"), "Booking confirmation not found.");
    }

    @Test(priority = 2, dependsOnMethods = "confirmBookingAndFetchOrderId")
    public void accessMyItinerary() {
    	ItineraryPage itinerary = new ItineraryPage(driver);
        itinerary.clickMyItinerary();

        Assert.assertTrue(driver.getTitle().contains("Booked Itinerary"), "Not navigated to itinerary page.");
    }

    @Test(priority = 3, dependsOnMethods = "accessMyItinerary")
    public void searchOrderId() {
    	ItineraryPage itinerary = new ItineraryPage(driver);
        itinerary.searchOrderId(orderId);

        Assert.assertTrue(driver.getPageSource().contains(orderId), "Order ID not found in itinerary.");
    }

    @Test(priority = 4, dependsOnMethods = "searchOrderId")
    public void cancelBooking() throws InterruptedException {
    	ItineraryPage itinerary = new ItineraryPage(driver);

        boolean bookingPresent = itinerary.isBookingPresent(orderId);

        if (bookingPresent) {
            itinerary.cancelBooking(orderId);
            Thread.sleep(2000); // wait for cancellation to process

            // Search again to confirm removal
            itinerary.searchOrderId(orderId);
            Assert.assertFalse(driver.getPageSource().contains(orderId), "Booking was not removed.");
        } else {
            System.out.println("Booking was already canceled.");
        }
    }

    @Test(priority = 5, dependsOnMethods = "cancelBooking")
    public void cancelAlreadyCanceledBooking() {
        ItineraryPage itinerary = new ItineraryPage(driver);

        boolean present = itinerary.isBookingPresent(orderId);

        if (!present) {
            System.out.println("Attempting to cancel already canceled booking...");
            boolean cancelled = itinerary.tryCancelAgain(orderId);
            Assert.assertFalse(cancelled, "Booking cancellation should have no effect or error.");
        }
    }
}
