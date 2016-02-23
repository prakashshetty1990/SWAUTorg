package PageMethods;


import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.Common;






/**
 * Base class for all the pages.
 *
 */
public abstract class Page {
	protected WebDriver browser;	
	protected abstract boolean isValidPage();

	protected abstract void waitForPageLoad();

	/**
	 * Constructor for Page class 
	 * @param browser
	 * @param report
	 */
	protected Page(WebDriver browser) {
		this.browser=browser;		
		PageFactory.initElements(browser, this);
		waitForPageLoad();
		verifyApplicationInCorrectPage();
	}

	/**
	 * Verify Application in Correct Page. 
	 * @param Nil 
	 * @return Nil
	 */	

	private void verifyApplicationInCorrectPage() {
		if (!isValidPage()) {
			String stepName="Navigation to Page";
			String message="The application is not in the expected page , current page: " + 
					browser.getTitle() +" Page.";
			//report.reportFailEvent(stepName, message);			
		}
	}
	
	
	/**
	 * Check if the element is present in the page
	 * @param element WebElement need to check
	 * @return True if present
	 */
	protected boolean isElementPresent(WebElement element){
		try{
			return element.isDisplayed();			
		}catch(NoSuchElementException ex){
			return false;
		}catch(StaleElementReferenceException ex2){
			return false;
		}
	}

	
	/**
	 * Check if the element is present in the page
	 * @param element WebElement need to check
	 * @return True if present
	 */
	public boolean isElementPresent(By by){
		try{
			return browser.findElement(by).isDisplayed();
		}catch(NoSuchElementException ex){
			return false;
		}catch(StaleElementReferenceException ex2){
			return false;
		}
	}


	/**
	 * Check if the element is present in the page and report error
	 * @param element
	 * @param errorMsg error message need to report if the element not present
	 */
	protected void isElementPresent(WebElement element,String stepName,String errorMsg) {
		if(!isElementPresent(element)){
			//report.reportFailEvent(stepName, errorMsg);
		}
	}

	/***
	 * Method to click on a link(WebElement button)
	 * @param : Element Name
	 ***/
	public void clickOn(WebElement we,String elem) {		
		try{
			waitForElement(we);
			if (isElementPresent(we)){
				we.click();
				Common.testStepPassed("Clicked on WebElement-"+ elem );	
			}}
		catch (Exception ex) {
			Common.testStepFailed("Uanble to click on Element-"+ elem);
		} 
	}

	/**
	 * Method to click on a link(WebElement link)
	 * @param : Element Name
	 */
	protected void jsClick(WebElement we,String elem) {		
		try{			
			((JavascriptExecutor) browser).executeScript("arguments[0].click();",we);
			Common.testStepPassed("Clicked on -"+ elem +"- Element");			
		}catch (RuntimeException ex) {
			Common.testStepFailed("Uanble to click on -"+ elem +"- Element");
		} 
	}
			
	/***
	 * Method to enter text in a textbox
	 * 
	 * @param : Element Name
	 * @return :
	 ***/
	public void enterText(WebElement we,String text){
		try{
			waitForElement(we);
			if(isElementPresent(we)){
				we.clear();
				we.sendKeys(text);	
				Common.testStepPassed("Entered value in the text field->"+ text);		
			}
		}
		catch (RuntimeException ex) {			
			Common.testStepFailed("Unable to enter text in the text field->"+ text);
		} 
	}

	/***
	 * Method to clear text in a textbox
	 * 
	 * @param : Element Name
	 * @return :
	 ***/
	public void clearText(WebElement we){
		try{
			waitForElement(we);
			if(isElementPresent(we)){
				we.clear();				
			}
		}catch(RuntimeException ex){
			Common.testStepFailed("Unable to clear text in the text field");
		}
	}

		
	/***
	 * Method to switch to child window
	 * @param : parentWindow
	 ***/
	public void navigateToNewWindow(String pageTitle) {
		boolean blnNavigate=false;
		try{			
			Set<String> handles = browser.getWindowHandles();
			if(handles.size()==1){
				sleep(7000);
				handles = browser.getWindowHandles();
			}
			if(handles.size()>1){
				for (String windowHandle : handles) {					
					String strActTitle = browser.switchTo().window(windowHandle).getTitle();
					if(strActTitle.contains(pageTitle)){
						blnNavigate = true;
						browser.manage().window().maximize();
						sleep(5000);				
						Common.testStepPassed("Navigated to the page -"+pageTitle+"- successfully");	
						break;
					}					
				}
				if(!blnNavigate){
					Common.testStepFailed("Unable to Navigate to the page -"+pageTitle);
				}
			}else{
				Common.testStepFailed("Unable to Navigate to the page -"+pageTitle);
			}
		}
		catch(RuntimeException ex){
			Common.testStepFailed("Unable to Navigate to the page -"+pageTitle);
		}
	}

	/***
	 * Method to switch to parent window
	 * @param : parentWindow
	 ***/
	public void navigatoToParentWindow(String parentWindow) {
		browser.switchTo().window(parentWindow);
	}
	
	public void jsmoveToElement(WebElement elem){
		
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript(mouseOverScript, elem);
		
	}

	/***
	 * Method to close a webpage
	 * @return      : 
	 ***/
	public void closeCurrentPage(){
		String str=browser.getTitle();
		try {
			browser.close();
			Set<String> windows=browser.getWindowHandles();
			for(String window:windows){
				browser.switchTo().window(window);
			}
			sleep(5000);
			Common.testStepPassed("Closed the current page with title->"+str);
		} catch (Exception e) {
			Common.testStepFailed("Unable to Close the current page with title->"+str);
		}
	}

	
	//*****************************************************************************************************************//
	//Alert pop ups
	//*****************************************************************************************************************//
	/***
	 * Method to accept and close alert and return the text within the alert
	 * 
	 * @param : 
	 * @return :
	 ***/
	public String closeAlertAndReturnText(){
		String alertMessage=null;
		try{		
			WebDriverWait wait = new WebDriverWait(browser, 40);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = browser.switchTo().alert();
			alertMessage=alert.getText();
			//report.reportPassEvent("alertMessage", "alertMessage displayed is->"+alertMessage);
			alert.accept();
		}catch(Exception Ex){
			//report.reportFailEvent("Exception Caught", "Message is->"+Ex.getMessage());
		}
		return alertMessage;
	}



	//*****************************************************************************************************************//
	//waits
	//*****************************************************************************************************************//

	/**
	 * Method to wait for element to load in the page
	 * @param WebElement
	 */
	protected Boolean waitForElement(By by) {
		try {
			new WebDriverWait(browser,30).
			until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
	
	/**
	 * Method to wait for element to load in the page
	 * @param WebElement
	 */

	protected Boolean waitForElement(WebElement we) {
		try {
			new WebDriverWait(browser, 30).until(ExpectedConditions
					.visibilityOf(we));
			return true;
		} catch (RuntimeException ex) {
			return false;
		}    	
	}

	/**
	 * Method to wait for Alert present in the page
	 * @param 
	 */

	protected Boolean waitForAlert(){
		try{
			new WebDriverWait(browser, 60).until(ExpectedConditions.alertIsPresent());
			return true;
		}catch(Exception Ex){
			return false;
		}
	}


	/***
	 * Method to get current time in minutes
	 * @param : Element Name
	 * @return :
	 * Modified By :
	 ***/
	public int getTimeInMin (String time) {
		//String time=new SimpleDateFormat("HH:mm").format(new Date());
		String[] splitTime=time.split(":");
		int hr=Integer.parseInt(splitTime[0]);
		int mn=Integer.parseInt(splitTime[1].substring(0,2));
		if(hr>12){
			hr=hr-12;
		}
		int timStamp=(hr*60)+mn;
		return timStamp;
	}


	/***
	 * Method to check for an alert for 5 seconds
	 * @param       : Element Name
	 * @return      : 
	 * Modified By  :  
	 ***/

	public boolean isAlertPresent(){
		try{
			WebDriverWait wait = new WebDriverWait(browser, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		}catch(Exception e){			
			return false;
		}
	}


	/***
	 * Method to wait for the any of 2 elements to be displayed
	 * @param       : we1,we2
	 * @return      : 
	 * @author      : Prakash Shetty
	 * Modified By  :  
	 ***/


	public boolean waitForAnyElement(WebElement we1,WebElement we2){
		try{
			for(int i=0;i<60;i++){
				if(isElementPresent(we1)||isElementPresent(we2)){
					break;
				}else{
					sleep(1000);
				}
			}
			return true;
		}catch(Exception Ex){
			return false;
		}
	}
	
	
	/**
	 * method to make a thread sleep for customized time in milliseconds
	 * @param milliseconds
	 */
	protected void sleep(int milliseconds){
		 try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Method to wait for the any of 2 elements to be displayed
	 * @param       : By,By
	 * @return      : 
	 * @author      : Prakash Shetty
	 * Modified By  :  
	 ***/


	public boolean waitForAnyElement(By we1,By we2){
		try{
			for(int i=0;i<80;i++){
				if(isElementPresent(we1)||isElementPresent(we2)){
					break;
				}else{
					sleep(1000);
				}
			}
			return true;
		}catch(Exception Ex){
			return false;
		}
	}
	
	
	/***
	 * Method to hover over an element
	 * @param       : weMainMenuElement,weSubMenuElement
	 * @return      : 
	 * @author      : Prakash Shetty
	 * Modified By  :  
	 ***/

	public void clickOnSubMenu(WebElement weMain,WebElement weSub ){
		String strElem=null;
		try{
			Actions action = new Actions(browser);
			action.moveToElement(weMain).click().perform();
		}catch(Exception Ex){
			Common.testStepFailed("Unable to hover Over main menu Item");
		}
		try{
		waitForElement(weSub);
		strElem = weSub.getText();
		weSub.click();
		}catch(Exception ex){}
		sleep(2000);
		Set<String> handles = browser.getWindowHandles();
		if(handles.size()>1){
			Common.testStepPassed("Clicked on Link Life Insurance successfully");							
		}else{
			jsClick(weSub, strElem);
		}
		
	}
	
	/***
	 * Method to Select value from dropdown by visible text
	 * @param       : we,strElemName,strVisibleText
	 * @return      : 
	 * @author      : Prakash Shetty
	 * Modified By  :  
	 ***/
	
	public void selectByVisisbleText(WebElement we,String strElemName,String strVisibleText){
		try{
			Select sel = new Select( we);
			sel.selectByVisibleText(strVisibleText);
			Common.testStepPassed("selected value -"+strVisibleText +" from dropdown->"+strElemName);
		}catch(Exception Ex){
			Common.testStepFailed("Unable to select value from the dropdown "+Ex.getMessage());
		}
	}
	
	
	
	public void selectByVisisbleValue(WebElement we,String strElemName,String strVisibleValue){
		try{
			Select sel = new Select( we);
			sel.selectByValue(strVisibleValue);
			Common.testStepPassed("selected value -"+strVisibleValue +" from dropdown->"+strElemName);
		}catch(Exception Ex){
			Common.testStepFailed("Unable to select value from the dropdown "+Ex.getMessage());
		}
	}

}
