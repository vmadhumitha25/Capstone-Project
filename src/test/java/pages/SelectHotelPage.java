package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SelectHotelPage {

	WebDriver driver;

	public SelectHotelPage(WebDriver driver) {
		this.driver = driver;
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
	
	public String getSelectedHotelName() {
        return hotelName.getAttribute("value");
    }

    public String getSelectedRoomType() {
        return roomType.getAttribute("value");
    }

    public String getSelectedPrice() {
        return price.getAttribute("value");
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
