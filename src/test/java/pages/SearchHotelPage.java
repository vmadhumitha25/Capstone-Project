package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import utilis.Utility;

public class SearchHotelPage {
	WebDriver driver;

	@FindBy(xpath = "//td[@class='welcome_menu']")
	WebElement userID;

	@FindBy(xpath = "//a[text()='Logout']")
	WebElement logoutButton;

	@FindBy(xpath = "//a[text()='Booked Itinerary']")
	WebElement bookitinerary;

	@FindBy(xpath = "//a[text()='Change Password']")
	WebElement changepassword;

	@FindBy(id = "location")
	WebElement locationDropdown;

	@FindBy(id = "hotels")
	WebElement hotelsDropdown;

	@FindBy(id = "room_type")
	WebElement roomTypeDropdown;

	@FindBy(id = "room_nos")
	WebElement noOfRoomsDropdown;

	@FindBy(id = "datepick_in")
	WebElement checkInDateField;

	@FindBy(id = "datepick_out")
	WebElement checkOutDateField;

	@FindBy(id = "adult_room")
	WebElement adultsPerRoomDropdown;

	@FindBy(id = "child_room")
	WebElement childrenPerRoomDropdown;

	@FindBy(id = "Submit")
	WebElement searchButton;

	@FindBy(id = "Reset")
	WebElement resetButton;

	@FindBy(id = "checkin_span")
	WebElement checkInErrorMsg;

	@FindBy(id = "checkout_span")
	WebElement checkOutErrorMsg;

	@FindBy(xpath = "//td[contains(text(),'Select Hotel')]")
	WebElement selectHotelPageHeader;

	@FindBy(id = "radiobutton_1")
	WebElement firstHotelRadioButton;

	@FindBy(linkText = "Search Hotel")
	WebElement SearchHotel;
	
	@FindBy(className = "reg_error")  // General error message element if any
	WebElement generalError;

	// Constructor
	public SearchHotelPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String userverifyID() {
		WebElement welcomemessage = driver.findElement(By.xpath("//td[@class='welcome_menu']"));
		Assert.assertTrue(welcomemessage.isDisplayed(), "The userId is not displayed");
		return userID.getText();
	}

	public void searchHotel(String location, String hotel, String roomType, String roomNos, String checkIn,
			String checkOut, String adults, String children) throws InterruptedException {
		if (!location.isEmpty())
			locationDropdown.sendKeys(location);
		if (!hotel.isEmpty())
			hotelsDropdown.sendKeys(hotel);
		if (!roomType.isEmpty())
			roomTypeDropdown.sendKeys(roomType);
		if (!roomNos.isEmpty())
			noOfRoomsDropdown.sendKeys(roomNos);
		if (!checkIn.isEmpty())
			checkInDateField.clear();
		checkInDateField.sendKeys(checkIn);
		if (!checkOut.isEmpty())
			checkOutDateField.clear();
		checkOutDateField.sendKeys(checkOut);
		if (!adults.isEmpty())
			adultsPerRoomDropdown.sendKeys(adults);
		if (!children.isEmpty())
			childrenPerRoomDropdown.sendKeys(children);

		searchButton.click();
		Thread.sleep(1000);
		//SearchHotel.click();

	}

	public void clickSearchHotel() {
		SearchHotel.click();
	}

	public void clickReset() {
		resetButton.click();
	}

	// === Validations ===
	public boolean selectHotelPage() {
		return selectHotelPageHeader.isDisplayed();
	}

	public String getCheckInError() {
		return checkInErrorMsg.getText();
	}

	public String getCheckOutError() {
		return checkOutErrorMsg.getText();
	}

	public boolean isHotelListDisplayed() {
		return firstHotelRadioButton.isDisplayed();
	}
	public boolean isErrorDisplayed() {
	    return checkInErrorMsg.isDisplayed() || checkOutErrorMsg.isDisplayed() || generalError.isDisplayed();
	}
	
	public String getDisplayedErrorMessage() {
	    if (checkInErrorMsg.isDisplayed()) {
	        return checkInErrorMsg.getText();
	    } else if (checkOutErrorMsg.isDisplayed()) {
	        return checkOutErrorMsg.getText();
	    } else if (generalError.isDisplayed()) {
	        return generalError.getText();
	    }
	    return "No error message displayed";
	}
	
	public void testSearchWithPropertiesFile(){

		SearchHotelPage searchPage = new SearchHotelPage(driver);
		
		locationDropdown.sendKeys(Utility.getProperty("location"));
	    hotelsDropdown.sendKeys(Utility.getProperty("hotel"));
	    roomTypeDropdown.sendKeys(Utility.getProperty("roomType"));
	    noOfRoomsDropdown.sendKeys(Utility.getProperty("numRooms"));
	    checkInDateField.clear();
	    checkInDateField.sendKeys(Utility.getProperty("checkIn"));
	    checkOutDateField.clear();
	    checkOutDateField.sendKeys(Utility.getProperty("checkOut"));
	    adultsPerRoomDropdown.sendKeys(Utility.getProperty("adultsPerRoom"));
	    childrenPerRoomDropdown.sendKeys(Utility.getProperty("childrenPerRoom"));
	    searchButton.click();
	    
	}
}
