package TestCases;

import java.io.File;

import org.testng.annotations.BeforeClass;
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
import Utilities.TestLinkConnection;
import Utilities.XmlValidation;

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


	@BeforeClass
	public void start(){	
		GenericKeywords.extent.loadConfig(new File("./Config/extent-config.xml"));
		GenericKeywords.extent.addSystemInfo("Browser", Common.getConfigProperty("Browser"));
		GenericKeywords.extent.addSystemInfo("URL", Common.getConfigProperty("url"));
	}


	public void testStart(String testCaseDescription) {
		GenericKeywords.testFailure = false;
		GenericKeywords.currentStep = 0;
		reportStart(GenericKeywords.testCaseName,testCaseDescription);
		adactinApplication=new AdactinApplication();
		adactinHomePage=adactinApplication.openAdactinApplication();

	}

	public void testEnd() {
		try {
			adactinApplication.close();			
		} catch (Exception e) {
			System.out.println("Expception : " + e.getMessage());
		}finally{
			GenericKeywords.extent.endTest(GenericKeywords.parent);
			GenericKeywords.extent.flush();	
			if(getConfigProperty("UpdateTestLinkStatus").toString().trim().toUpperCase().equals("YES")){
				if(GenericKeywords.testFailure){
					TestLinkConnection.updateExecutionStatus(GenericKeywords.testCaseName, "", "FAIL");					
				}else{
					TestLinkConnection.updateExecutionStatus(GenericKeywords.testCaseName, "passed", "PASS");
				}
			}
		}
	}


	@Test(alwaysRun = true)
	public void TC_001() {
		GenericKeywords.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Login to Adactin Application");


		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			searchHotel = adactinHomePage.Login();			
			if(searchHotel.verifyLoginpage())
				searchHotel.logoutFromApp();

			testStepInfoEnd(testDataSet);
		}
		testEnd();

	}


	@Test(alwaysRun = true)
	public void TC_002() {
		GenericKeywords.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Search a hotel");


		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			searchHotel = adactinHomePage.Login();			
			selectHotel=searchHotel.BookHotel();

			testStepInfoEnd(testDataSet);
		}
		testEnd();

	}


	@Test(alwaysRun = true)
	public void TC_003() {
		GenericKeywords.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Book a hotel");


		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet );

			searchHotel = adactinHomePage.Login();			
			selectHotel=searchHotel.BookHotel();
			selectHotel.Selecthotel();

			testStepInfoEnd(testDataSet);
		}
		testEnd();

	}


	@Test(alwaysRun = true)
	public void TC_004() {
		GenericKeywords.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Forget Password functionality");


		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			ForgetPswd=adactinHomePage.forgetpassword();

			testStepInfoEnd(testDataSet );
		}
		testEnd();

	}


	@Test(alwaysRun = true)
	public void TC_005() {
		GenericKeywords.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("New User Register functionality");


		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			Registrationpage=adactinHomePage.UserRegistration();

			testStepInfoEnd(testDataSet );
		}
		testEnd();

	}


	@Test(alwaysRun = true)
	public void TC_006() {
		GenericKeywords.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("XML Validation");

		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			XmlValidation.SoapCollateralPositionData();	

			try {
				XmlValidation.SoapCollateralPositionData();	
				XmlValidation.validateXml();			
			} catch (Exception e) {

			} 

			testStepInfoEnd(testDataSet);
		}
		testEnd();

	}

}
