package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.ProjectSpecification;
import utilis.ConfigReader;

public class LoginPage extends ProjectSpecification{

	WebDriver driver;
	
	@FindBy(id ="username")
	WebElement Username;
	
	By userprop = By.name("username");
	
	@FindBy(id = "password")
	WebElement Password;
	
	By passProp = By.name("password");
	
	@FindBy(id="login")
	WebElement LoginBtn;
	
	By submitBtn = By.className("login_button");
	
	@FindBy(xpath = "//a[text()='New User Register Here']")
	WebElement Newuser;
	
	@FindBy(linkText = "Logout")
	WebElement logoutBtn;
	
	@FindBy(id = "error")
	WebElement errorMessage;
	
	@FindBy(className = "login_error") // Error class for invalid login
	WebElement loginErrorMsg;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this );
	}
	
	public LoginPage EnterUsername(String user) {
		Username.sendKeys(user);
		return this;
	}
	public LoginPage EnterPassword(String passwd) {
		Password.sendKeys(passwd);
		return this;
	}
	
	public void loginButton() {
		LoginBtn.click();
	}
	
	/*public Searchhotelpage Login() throws Exception {
		Login.click();
	    return new Searchhotelpage(driver);
	}*/
	public RegisterPage Newuser() {
		Newuser.click();
		return new RegisterPage(driver);
	}
	public void logoutButton() {
		logoutBtn.click();
	}
	
	// Is logout button displayed
	public boolean logooutButtonDisplayed() {
		return logoutBtn.isDisplayed();
	}
	
    public boolean errorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }
    
	public void loginFunction(String userName, String passWord) {
		EnterUsername(userName);
		EnterPassword(passWord);	
		LoginBtn.click();
		logoutButton();  
	}
	
	public void loginWithPropertyCredentials() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String username = ConfigReader.getProperty("user.email");
	    String password = ConfigReader.getProperty("user.password");
	    
	    System.out.println("Logging in with: " + username + " | " + password);
	    //loginFunction(username, password);
	    EnterUsername(username);
		EnterPassword(password);	
		LoginBtn.click();
	}
	
	public boolean loginSuccessful() {
        return driver.getTitle().contains("Search Hotel");
    }

    public boolean loginFailed() {
        return loginErrorMsg.isDisplayed();
    }
}
