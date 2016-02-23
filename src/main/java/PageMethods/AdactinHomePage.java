package PageMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.Common;

public class AdactinHomePage extends Page {

	@FindBy(xpath="//input[@name='username']")
	private WebElement weUsername;
	
	@FindBy(xpath="//input[@name='password']")
	private WebElement wePassword;
	
	@FindBy(xpath="//input[@name='login']")
	private WebElement btnLogin;
	
	@FindBy(xpath="//a[contains(@href,'Register.php')]")
	private WebElement Registerlink;
	
	@FindBy(xpath="//a[contains(@href,'ForgotPassword.php')]")
	private WebElement weForgetpswdlink;
	
	@FindBy(xpath="//*[@id='emailadd_recovery']")
	private WebElement weEmailAddress;

	@FindBy(xpath="//*[@id='Submit']")
	private WebElement weSubmit;
	
	@FindBy(xpath="//a[contains(@href,'Register.php')]")
	private WebElement weNewRegister;
	
	
	@FindBy(xpath="//*[@id='username']")
	private WebElement weNewusername;
	@FindBy(xpath="//*[@id='password']")
	private WebElement weNewPassword;
	
	@FindBy(xpath="//*[@id='re_password']")
	private WebElement weRePassword;
	
	@FindBy(xpath="//*[@id='full_name']")
	private WebElement weFullName;
		
	@FindBy(xpath="//*[@id='email_add']")
	private WebElement weEmailAdd;
	
	@FindBy(xpath="//*[@id='tnc_box']")
	private WebElement chkAgree;
	
	@FindBy(xpath="//*[@id='Submit']")
	private WebElement btnRegister;
	
	protected static String HOME_PAGE_TITLE = "AdactIn.com - Hotel Reservation System";

	public AdactinHomePage(WebDriver browser) {
		super(browser);		
	}

	@Override
	protected boolean isValidPage() {
		if (browser.getTitle().trim().contains(HOME_PAGE_TITLE)) {
			return true;
		}
		return false;
	}

	@Override
	protected void waitForPageLoad() {
		try{
			new WebDriverWait(browser,30).
			until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='login']")));				
		}catch(Exception e){
			System.out.println(e.getMessage());			
		}
	}
	
	
	//==========================================================
	
	public SearchHotel Login()
	{
						
		//System.out.println("username -> "+Common.retrieve("UserName"));
		enterText(weUsername,Common.retrieve("UserName")); 
		
		enterText(wePassword,Common.retrieve("Password")); 
		clickOn(btnLogin,"Login");
		return new SearchHotel(browser);
	}
	public ForgetPassword forgetpassword()
	{
		clickOn(weForgetpswdlink,"ForgetPassword");
		enterText(weEmailAddress,Common.retrieve("EmailAddress")); 
		clickOn(weSubmit,"Submit");
		return new ForgetPassword(browser);
		
		
	}
	public NewUserRegistration UserRegistration()
	{
		clickOn(weNewRegister,"New Registration");
		enterText(weNewusername,Common.retrieve("UserName")); 
		enterText(weNewPassword,Common.retrieve("Password")); 
		enterText(weRePassword,Common.retrieve("RePassword")); 
		enterText(weFullName,Common.retrieve("FullName")); 
		enterText(weEmailAdd,Common.retrieve("EmailAddress")); 
		clickOn(chkAgree,"Agree");
		clickOn(btnRegister,"Register");
		
		
		return new NewUserRegistration(browser);
	}
	
	
}
