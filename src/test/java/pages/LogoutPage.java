package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import base.ProjectSpecification;
import utilis.Utility;

public class LogoutPage extends ProjectSpecification{

	WebDriver driver;
	
	@FindBy(xpath = "//a[text()='Click here to login again']")
	WebElement logoutsuccess;
	
	By LoginButton = By.id("login2");
	
	By LogoutButton = By.id("logout");
	
	public LogoutPage (WebDriver driver) {
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean logoutBtnDisplayed() {
		return driver.findElement(LogoutButton).isDisplayed();
	}
	
	public boolean loginPageDisplayed() {
        return ProjectSpecification.driver.getCurrentUrl().contains("index.php");
    }
	
	public LoginPage logoutsuccessM() {
		logoutsuccess.click();
		return new LoginPage(driver);
	}
	
	public void clickLogoutBtn() {
		driver.findElement(LogoutButton).click();
		String expectedUrl = "https://adactinhotelapp.com/index.php";  
	    Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed for valid credentials");
	}
	
}
