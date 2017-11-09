package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIResults;
import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.Attachment;

public class TestLinkConnection extends Common{

	public static String result = "";
	public static String SERVER_URL = "";
	public static String DEV_KEY = ""; //Your API
	public static String PROJECT_NAME = ""; 
	public static String PLAN_NAME = "";
	public static String BUILD_NAME = "";
	public static int EXECUTION_ID = 0;
	public static TestLinkAPI api = null;
	public static URL testlinkURL = null;
	public static int TESTSUITENUM = 0;
	public static String am_pm;
	public static String min; 
	public static String hr;
	public static String sec;
	public static int yr;
	public static String mon;
	public static String day;

	public static void updateExecutionStatus(String testCaseName,String notes,String strStatus){
		if(strStatus.toLowerCase().contains("pass")){
			result = TestLinkAPIResults.TEST_PASSED;
		}else if(strStatus.toLowerCase().contains("fail")){
			notes = getNotesFromLogFile();
			result = TestLinkAPIResults.TEST_FAILED;
		}else{
			Common.testStepInfo("Status sent to TestLink -"+strStatus+"- is not valid, Only pass and fail is allowed");
		}		
		updateTestLinkResult(testCaseName, notes, result);		
	}



	private static void updateTestLinkResult(String testCase, String exception, String result){		
		try {
			TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(
					DEV_KEY, SERVER_URL);
			testlinkAPIClient.reportTestCaseResult(PROJECT_NAME, PLAN_NAME,
					testCase, BUILD_NAME, exception, result);			
		} catch (Exception e) {
			Common.testStepInfo("Unable to update the status for TestCase->"+testCase);
		}		
	}

	public static String getNotesFromLogFile(){
		String FILENAME = GenericKeywords.outputDirectory + "/LogFile.log";
		BufferedReader br = null;
		FileReader fr = null;
		String Notes="";
		try {
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.contains(GenericKeywords.testCaseName.trim())){		
					if(sCurrentLine.contains("ERROR")){
						String[] str1 = sCurrentLine.split(GenericKeywords.testCaseName+":");
						sCurrentLine = str1[1]; 
						sCurrentLine = "ERROR : " +sCurrentLine;
					}else if(sCurrentLine.contains("INFO")){
						String[] str1 = sCurrentLine.split(GenericKeywords.testCaseName+":");
						sCurrentLine = str1[1]; 
						sCurrentLine = "INFO : " +sCurrentLine;
					}else if(sCurrentLine.contains("WARN")){
						String[] str1 = sCurrentLine.split(GenericKeywords.testCaseName+":");
						sCurrentLine = str1[1]; 
						sCurrentLine = "WARN : " +sCurrentLine;
					}else {
						String[] str1 = sCurrentLine.split(GenericKeywords.testCaseName+":");
						sCurrentLine = str1[1]; 						
					}
					Notes = Notes + System.lineSeparator() + sCurrentLine;
				}				
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		return Notes;
	}

	public static void uploadAttachmentToTestSuite(String attachmentPath){
		String attachmentName = "";
		Calendar calendar = new java.util.GregorianCalendar();

		hr = "0" + calendar.get(10);
		hr = hr.substring(hr.length() - 2);

		min = "0" + calendar.get(12);
		min = min.substring(min.length() - 2);

		sec = "0" + calendar.get(13);
		sec = sec.substring(sec.length() - 2);

		yr = calendar.get(1);

		mon = "0" + (calendar.get(2) + 1);
		mon = mon.substring(mon.length() - 2);

		day = "0" + calendar.get(5);
		day = day.substring(day.length() - 2);

		if (calendar.get(9) == 0) {
			am_pm = "AM";
		} else {
			am_pm = "PM";
		}    
		attachmentName = yr + "_" + mon + "_" + day + "_" + hr + "_" + min + "_" + sec + "_" + am_pm;
		try {
			testlinkURL = new URL(SERVER_URL);
			api = new TestLinkAPI(testlinkURL, DEV_KEY);
			File attachmentFile = new File(attachmentPath);					
			String fileContent = null;
			try {
				byte[] byteArray = FileUtils.readFileToByteArray(attachmentFile);
				fileContent = new String(Base64.encodeBase64(byteArray));

				//if(!(EXECUTION_ID==0)){
				Attachment attachment = api.uploadTestSuiteAttachment(TESTSUITENUM, attachmentName, "Execution Results", "Results.html", "text/html", fileContent);					
				/*}else{
					Common.testStepInfo("Latest Execution ID is not found for TestCase->"+attachmentName);					
				}*/

			} catch (Exception e) {
				Common.testStepInfo("Unable to attach the file in following path->"+attachmentPath);		
			}

		} catch (Exception mue) {
			Common.testStepInfo("Unable to attach the file to TestLink in following path->"+attachmentPath);
		}
	}

	private static void uploadAttachmentToTestCase(String testCase,String attachmentPath){
		try {
			testlinkURL = new URL(SERVER_URL);
			api = new TestLinkAPI(testlinkURL, DEV_KEY);
			File attachmentFile = new File(attachmentPath);					
			String fileContent = null;
			try {
				byte[] byteArray = FileUtils.readFileToByteArray(attachmentFile);
				fileContent = new String(Base64.encodeBase64(byteArray));

				if(!(EXECUTION_ID==0)){
					Attachment attachment = api.uploadExecutionAttachment(EXECUTION_ID, testCase, "Execution Results", "Results.html", "text/html", fileContent);
					//api.uploadTestSuiteAttachment(4, testCase, "Execution Results", "Results.html", "text/html", fileContent);
				}else{
					Common.testStepInfo("Latest Execution ID is not found for TestCase->"+testCase);					
				}

			} catch (Exception e) {
				Common.testStepInfo("Unable to attach the file in following path->"+attachmentPath);		
			}

		} catch (Exception mue) {
			Common.testStepInfo("Unable to attach the file to TestLink in following path->"+attachmentPath);
		}
	}
}
