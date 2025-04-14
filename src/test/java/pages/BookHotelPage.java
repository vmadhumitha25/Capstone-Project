package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookHotelPage {

	WebDriver driver;

	// Constructor
	public BookHotelPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); // initialize WebElements
	}

	// WebElements using @FindBy
	@FindBy(id = "first_name")
	WebElement firstName;

	@FindBy(id = "last_name")
	WebElement lastName;

	@FindBy(id = "address")
	WebElement address;

	@FindBy(id = "cc_num")
	WebElement creditCardNumber;

	@FindBy(id = "cc_type")
	WebElement creditCardType;

	@FindBy(id = "cc_exp_month")
	WebElement expMonth;

	@FindBy(id = "cc_exp_year")
	WebElement expYear;

	@FindBy(id = "cc_cvv")
	WebElement cvv;

	@FindBy(id = "book_now")
	WebElement bookNowButton;

	@FindBy(xpath = "//span[@class='reg_error']")
	WebElement errorMsg;

	@FindBy(id = "cancel")
	WebElement cancelBtn;

	@FindBy(id = "order_no")
	WebElement orderNumber;

	// Error locators
	@FindBy(id = "first_name_span")
	WebElement errFirstName;

	@FindBy(id = "last_name_span")
	WebElement errLastName;

	@FindBy(id = "address_span")
	WebElement errAddress;

	@FindBy(id = "cc_num_span")
	WebElement errCardNo;

	@FindBy(id = "cc_type_span")
	WebElement errCardType;

	@FindBy(id = "cc_cvv_span")
	WebElement errCVV;

	@FindBy(id = "hotel_name_dis")
	WebElement hotelNameDisplay;

	@FindBy(id = "room_type_dis")
	WebElement roomTypeDisplay;

	@FindBy(id = "total_price_dis")
	WebElement totalPriceDisplay;

	@FindBy(id = "order_no")
	WebElement orderNoField;

	@FindBy(id = "reset")
	WebElement resetBtn;

	public void fillBookingDetails(String fname, String lname, String addr, String cc, String type, String month,
			String year, String cvvNum) {

		System.out.println("Filling form with: " + fname + ", " + lname + ", " + addr);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 500);"); // Scroll down by 500px
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		firstName.sendKeys(fname);
		lastName.sendKeys(lname);
		address.sendKeys(addr);
		creditCardNumber.sendKeys(cc);

		Select cardType = new Select(creditCardType);
		cardType.selectByVisibleText(type);

		Select expMonthSelect = new Select(expMonth);
		expMonthSelect.selectByVisibleText(month);

		Select expYearSelect = new Select(expYear);
		expYearSelect.selectByVisibleText(year);
		cvv.sendKeys(cvvNum);
	}

	public void clickBookNow() {
		bookNowButton.click();
	}

	public String getErrorMessage() {
		return errorMsg.getText();
	}

	// Method to get the hotel name on the booking confirmation page
    public String getHotelName() {
        WebElement hotelNameElement = driver.findElement(By.cssSelector(".hotel-name"));
        return hotelNameElement.getText();
    }

    // Method to get the room type on the booking confirmation page
    public String getRoomType() {
        WebElement roomTypeElement = driver.findElement(By.cssSelector(".room-type"));
        return roomTypeElement.getText();
    }

    // Method to get the hotel location on the booking confirmation page
    public String getHotelLocation() {
        WebElement locationElement = driver.findElement(By.cssSelector(".hotel-location"));
        return locationElement.getText();
    }

    // Method to get the total price on the booking confirmation page
    public String getTotalPrice() {
        WebElement priceElement = driver.findElement(By.cssSelector(".total-price"));
        return priceElement.getText();
    }
	public String getOrderNumber() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement orderNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("order_no")));
		return orderNumber.getAttribute("value");
	}

	public void clickReset() {
		resetBtn.click();
	}

	public void clickCancel() {
		cancelBtn.click();
	}

	public String getBookingID() {
		return orderNoField.getAttribute("value");
	}
}
