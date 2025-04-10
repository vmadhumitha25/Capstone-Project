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

	public void fillBookingDetails(String fname, String lname, String addr, String cc, String type, String month,
			String year, String cvvNum) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 500);"); // Scroll down by 500px
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.visibilityOf(firstName));
			wait.until(ExpectedConditions.elementToBeClickable(firstName));
			firstName.clear();
			firstName.sendKeys(fname);
			System.out.println("First Name filled with: " + fname);
		} catch (Exception e) {
			System.out.println("ERROR filling first name: " + e.getMessage());
		}
		lastName.sendKeys(lname);
		address.sendKeys(addr);
		creditCardNumber.sendKeys(cc);

		new Select(creditCardType).selectByVisibleText(type);
		new Select(expMonth).selectByVisibleText(month);
		new Select(expYear).selectByVisibleText(year);

		creditCardType.sendKeys(type);
		expMonth.sendKeys(month);
		expYear.sendKeys(year);
		cvv.sendKeys(cvvNum);
		bookNowButton.click();
	}

	public void clickBookNow() {
		bookNowButton.click();
	}

	public String getErrorMessage() {
		return errorMsg.getText();
	}

	public String getHotelName() {
		return hotelNameDisplay.getAttribute("value");
	}

	public String getRoomType() {
		return roomTypeDisplay.getAttribute("value");
	}

	public String getTotalPrice() {
		return totalPriceDisplay.getAttribute("value");
	}

	public String getOrderNumber() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement orderNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("order_no")));
		return orderNumber.getAttribute("value");
	}

}
