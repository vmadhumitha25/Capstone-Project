package pages;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ItineraryPage {
	
	 WebDriver driver;

	    public ItineraryPage(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }

	    @FindBy(id = "my_itinerary")
	    WebElement myItineraryBtn;

	    @FindBy(id = "order_id_text")
	    WebElement searchBox;

	    @FindBy(id = "search_hotel_id")
	    WebElement goBtn;

	    @FindBy(name = "cancelall")
	    WebElement cancelSelected;

	    public void clickMyItinerary() {
	    	
	        myItineraryBtn.click();
	    }

	    public void searchOrderId(String orderId) {
	        searchBox.clear();
	        searchBox.sendKeys(orderId);
	        goBtn.click();
	    }

	    public boolean isBookingPresent(String orderId) {
	        try {
	            return driver.findElements(By.id("order_id_" + orderId)).size() > 0;
	        } catch (Exception e) {
	            return false;
	        }
	    }

	    public void cancelBooking(String orderId) {
	        try {
	            WebElement checkbox = driver.findElement(By.id("order_id_" + orderId));
	            checkbox.click();
	            cancelSelected.click();

	            Alert alert = driver.switchTo().alert();
	            alert.accept();
	        } catch (NoSuchElementException e) {
	            System.out.println("Booking already cancelled or not found: " + orderId);
	        }
	    }

	    public boolean tryCancelAgain(String orderId) {
	        try {
	            WebElement checkbox = driver.findElement(By.id("order_id_" + orderId));
	            checkbox.click();
	            cancelSelected.click();
	            Alert alert = driver.switchTo().alert();
	            alert.accept();
	            return true; // it should not reach here
	        } catch (NoSuchElementException e) {
	            return false; // expected path
	        }
	    }

}
