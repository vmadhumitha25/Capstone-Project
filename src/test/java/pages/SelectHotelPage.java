package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelectHotelPage {

	WebDriver driver;

	public SelectHotelPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	By radioButton = By.id("radiobutton_0");
	By continueBtn = By.id("continue");
	By errorMsg = By.id("radiobutton_span");

	// Hotel details before clicking Continue
    @FindBy(id = "hotel_name_0")
    WebElement hotelName;

    @FindBy(id = "room_type_0")
    WebElement roomType;

    @FindBy(id = "price_night_0")
    WebElement price;
    
	public void selectHotelAndContinue() {
		driver.findElement(radioButton).click();
		driver.findElement(continueBtn).click();
	}

	public void clickContinueWithoutSelection() {
		driver.findElement(continueBtn).click();
	}

	// Actions
	public void selectHotel() {
		driver.findElement(radioButton).click();
	}

	public void clickContinue() {
		driver.findElement(continueBtn).click();
	}
	
	// Method to get the selected hotel name
    public String getSelectedHotelName() {
        WebElement hotelNameElement = driver.findElement(By.cssSelector(".selected-hotel-name")); // Example selector
        return hotelNameElement.getText();
    }

    // Method to get the selected room type
    public String getSelectedRoomType() {
        WebElement roomTypeElement = driver.findElement(By.cssSelector(".selected-room-type")); // Example selector
        return roomTypeElement.getText();
    }

    // Method to get the selected hotel location
    public String getSelectedHotelLocation() {
        WebElement locationElement = driver.findElement(By.cssSelector(".selected-hotel-location")); // Example selector
        return locationElement.getText();
    }

    // Method to get the selected hotel price
    public String getSelectedPrice() {
        WebElement priceElement = driver.findElement(By.cssSelector(".selected-price")); // Example selector
        return priceElement.getText();
    }
    
	public String errorDisplayed() {
		return driver.findElement(errorMsg).getText();
	}
	public String getErrorMessage() {
		return driver.findElement(errorMsg).getText();
	}

	public boolean radioSelected() {
		return driver.findElement(radioButton).isSelected();
	}

	public boolean isHotelAvailable() {
		return driver.findElements(radioButton).size() > 0;
	}
	
	public boolean isOnBookingPage() {
        return driver.getCurrentUrl().contains("BookHotel");
    }
}
