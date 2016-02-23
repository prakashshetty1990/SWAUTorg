package TestCases;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import PageMethods.AdactinApplication;
import PageMethods.AdactinHomePage;
import PageMethods.ConfirmationPage;
import PageMethods.ForgetPassword;

import PageMethods.NewUserRegistration;
import PageMethods.SearchHotel;
import PageMethods.SelectHotel;
import Utilities.Common;
import Utilities.GenericKeywords;

@Listeners({ Utilities.TestListener.class })
public class TestCases extends Common {

	public static int count = 1;
	private AdactinApplication adactinApplication;
	private AdactinHomePage adactinHomePage;
	private SearchHotel searchHotel;
	private SelectHotel selectHotel;
	private ForgetPassword ForgetPswd;
	
    private ConfirmationPage confirmpage;
	private NewUserRegistration Registrationpage;
	public void testStart(String testCaseDescription) {
	

		GenericKeywords.testFailure = false;
		GenericKeywords.currentStep = 0;
		reportStart(testCaseDescription);
		adactinApplication=new AdactinApplication();
		adactinHomePage=adactinApplication.openAdactinApplication();
		//illinoisApplication = new IllinoisApplication();
		//illinoisLifeInsurePage = illinoisApplication.openRelevantApplication();				
	}

	public void testEnd() {
		try {
			adactinApplication.close();			
		} catch (Exception e) {
			System.out.println("Expception : " + e.getMessage());
		}
	}


	@Test(alwaysRun = true)
	public void TC_001() {

		testStart("Login to Adactin Application");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfo("########### Start of Test Case Data Set: "+testDataSet + " ###########");
			searchHotel = adactinHomePage.Login();			
			searchHotel.verifyLoginpage();
			testStepInfo("########### End of Test Case Data Set: "+testDataSet + " ###########");
			}
		testEnd();

	}
	@Test(alwaysRun = true)
	public void TC_002() {

		testStart("Search a hotel");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfo("########### Start of Test Case Data Set: "+testDataSet + " ###########");
			searchHotel = adactinHomePage.Login();			
			selectHotel=searchHotel.BookHotel();
		//	illinoisLifeInsurePage.getMyQuote();
		//	illinoisLifeInsurePage.verifyPathProtectorreturnOfPremiumTerms();
			
			testStepInfo("########### End of Test Case Data Set: "+testDataSet + " ###########");
			}
		testEnd();

	}

	@Test(alwaysRun = true)
	public void TC_003() {

		testStart("Book a hotel");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfo("########### Start of Test Case Data Set: "+testDataSet + " ###########");
			searchHotel = adactinHomePage.Login();			
			selectHotel=searchHotel.BookHotel();
			selectHotel.Selecthotel();
			
		//	illinoisLifeInsurePage.getMyQuote();
		//	illinoisLifeInsurePage.verifyPathProtectorreturnOfPremiumTerms();
			
			testStepInfo("########### End of Test Case Data Set: "+testDataSet + " ###########");
			}
		testEnd();

	}
	@Test(alwaysRun = true)
	public void TC_004() {

		testStart("Forget Password functionality");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfo("########### Start of Test Case Data Set: "+testDataSet + " ###########");
			ForgetPswd=adactinHomePage.forgetpassword();
			
		//	illinoisLifeInsurePage.getMyQuote();
		//	illinoisLifeInsurePage.verifyPathProtectorreturnOfPremiumTerms();
			
			testStepInfo("########### End of Test Case Data Set: "+testDataSet + " ###########");
			}
		testEnd();

	}
	
	@Test(alwaysRun = true)
	public void TC_005() {

		testStart("New User Register functionality");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfo("########### Start of Test Case Data Set: "+testDataSet + " ###########");
			Registrationpage=adactinHomePage.UserRegistration();
			
		//	illinoisLifeInsurePage.getMyQuote();
		//	illinoisLifeInsurePage.verifyPathProtectorreturnOfPremiumTerms();
			
			testStepInfo("########### End of Test Case Data Set: "+testDataSet + " ###########");
			}
		testEnd();

	}
	
}
