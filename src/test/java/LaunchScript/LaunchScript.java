package LaunchScript;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.GenericKeywords;

public class LaunchScript extends Common {

	
	@Test
	public void Launch(){
		try {			
			startup();
			TestNG testng = new TestNG();
			List<String> suites = new ArrayList<String>();
			suites.add("./Config/testng.xml");			
			testng.setOutputDirectory(GenericKeywords.outputDirectory+"/testng");
			testng.setTestSuites(suites);
			testng.run();		

		} catch (Exception e) {
			writeToLogFile("error", e.toString());
		} finally {
			try {
				report();
				cleanup();
				// logout();

			} catch (Exception e) {
				writeToLogFile("error", e.toString());
			} finally {
				writeToLogFile("INFO", "###################################");
				writeToLogFile("INFO", "Script Execution Complete");
				writeToLogFile("INFO", "####################################");

			}

		}
	}

}
