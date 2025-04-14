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
import org.testng.Assert;

import base.ProjectSpecification;

public class RegisterPage extends ProjectSpecification{
	
	public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "New User Register Here")
    WebElement registerLink;

    @FindBy(id = "username")
    WebElement username;

    @FindBy(name = "password")
    WebElement password;

    @FindBy(id = "re_password")
    WebElement confirmPassword;

    @FindBy(name = "full_name")
    WebElement fullName;

    @FindBy(name = "email_add")
    WebElement email;

    @FindBy(id = "captcha-form")
    WebElement captcha;
    
    @FindBy(name = "tnc_box")
    WebElement termsCheckbox;

    @FindBy(name = "Submit")
    WebElement registerButton;
	
    @FindBy(linkText = "Click here to login")
    WebElement verifyRegister;
    
    @FindBy(css = ".reg_success")
    WebElement registerSuccess;
    
    @FindBy(id = "email_add_span")
    WebElement emailErrorMsg;
    
    @FindBy(id = "captcha_span")
    WebElement captchaError;
    
    @FindBy(id = "re_password_span")
    WebElement passwordError;
    
    @FindBy(id = "username_span")
    WebElement usernameError;
    
    @FindBy(id = "Reset")
    WebElement ResetBtn;
    
    @FindBy(id = "Go back to Login page")
    WebElement goBackLogin;
    
    @FindBy(xpath = "//span[@class='reg_error']")
    WebElement errorMessage;
    
    public void clickRegisterLink() {
        registerLink.click();
    }

    public void enterUsername(String name) {
        username.clear();
        username.sendKeys(name);
    }

    public void enterPassword(String pass) {
        password.clear();
        password.sendKeys(pass);
    }

    public void enterConfirmPassword(String pass) {
        confirmPassword.clear();
        confirmPassword.sendKeys(pass);
    }

    public void enterFullName(String name) {
        fullName.clear();
        fullName.sendKeys(name);
    }

    public void enterEmail(String mail) {
        email.clear();
        email.sendKeys(mail);
    }

    public void enterCaptcha(String text) {
        captcha.clear();
        captcha.sendKeys(text);
    }

    public void acceptTerms() {
        if (!termsCheckbox.isSelected()) {
            termsCheckbox.click();
        }
    }

    public void clickRegisterButton() {
        registerButton.click();
    }

    public void goBackLoginPage() {
    	goBackLogin.click();
    }
    public void register(String user, String pass, String confirmPass, String name, String mail) {
        registerLink.click();
        username.sendKeys(user);
        password.sendKeys(pass);
        confirmPassword.sendKeys(confirmPass);
        fullName.sendKeys(name);
        email.sendKeys(mail);
        termsCheckbox.click();
       // registerButton.click();
    }
	
    public void clickResetButton() {
        ResetBtn.click();
    }
    
    public void verifyFormReset() {
        Assert.assertEquals(username.getAttribute("value"), "", "Username field is not reset.");
        Assert.assertEquals(password.getAttribute("value"), "", "Password field is not reset.");
        Assert.assertEquals(confirmPassword.getAttribute("value"), "", "Confirm Password field is not reset.");
        Assert.assertEquals(fullName.getAttribute("value"), "", "Full Name field is not reset.");
        Assert.assertEquals(email.getAttribute("value"), "", "Email field is not reset.");
        Assert.assertEquals(captcha.getAttribute("value"), "", "Captcha field is not reset.");
        Assert.assertFalse(termsCheckbox.isSelected(), "Terms checkbox should not be selected.");
    }
    
    public String verifySuccessfulRegistration() {
        String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl);
        return registerSuccess.getText();
    }

    public void verifyRegistrationFailure() {
        WebElement error = driver.findElement(By.id("errorMessage"));
        Assert.assertTrue(error.isDisplayed(), "Expected error message for failed registration.");
    }
	
    public String getErrorMessage() {
    	return errorMessage.getText();
    }
}
