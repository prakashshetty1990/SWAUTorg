package PageMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.Common;

public class SearchHotel extends Page {
	
	@FindBy(xpath="//*[@id='location']")
	private WebElement weLocation;

	@FindBy(xpath="//*[@id='hotels']")
	private WebElement weHotels;

	@FindBy(xpath="//*[@id='room_type']")
	private WebElement weRoomtype;
	
	@FindBy(xpath="//*[@id='room_nos']")
	private WebElement weRoomnos;
	
	@FindBy(xpath="//input[@name='datepick_in']")
	private WebElement Checkindate;
	
	@FindBy(xpath="//input[@name='datepick_out']")
	private WebElement Checkoutdate;
	
	@FindBy(xpath="//*[@id='adult_room']")
	private WebElement weAdults;
	
	@FindBy(xpath="//*[@id='child_room']")
	private WebElement weChildren;
	@FindBy(xpath="//input[@name='Submit']")
	private WebElement btnSearch;
	
	@FindBy(xpath="//input[@name='Reset']")
	private WebElement Reset;
	
	protected static String HOME_PAGE_TITLE = "AdactIn.com - Select Hotel";

	public SearchHotel(WebDriver browser) {
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
			new WebDriverWait(browser,5).
			until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='Reset']")));				
		}catch(Exception e){
			System.out.println(e.getMessage());
			//report.reportFailEvent("Check Illinois Life Insurance Home Page","Home Page is not displayed");
		}
	}

	public SelectHotel BookHotel()
	{
		
		selectByVisisbleText(weLocation,"Location",Common.retrieve("Location"));
		selectByVisisbleText(weHotels,"Hotel",Common.retrieve("Hotel"));
		selectByVisisbleText(weRoomtype,"RoomType",Common.retrieve("RoomType"));
		selectByVisisbleText(weRoomnos,"NumRooms",Common.retrieve("RoomNos"));
		
		enterText(Checkindate,Common.retrieve("CheckInDate")); 
		enterText(Checkoutdate,Common.retrieve("CheckOutDate")); 
		selectByVisisbleText(weAdults,"Adults",Common.retrieve("Adults"));
		selectByVisisbleText(weChildren,"Children",Common.retrieve("Children"));
		clickOn(btnSearch,"Search");
	return new SelectHotel(browser);
	}
	
	
	

	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

