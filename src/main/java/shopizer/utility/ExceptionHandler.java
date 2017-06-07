package shopizer.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;

/* A utility that use to interact with exception.*/
public class ExceptionHandler
{

	@SuppressWarnings("rawtypes")
	private static Map<ITestResult, List> verificationFailuresMap = new HashMap<ITestResult, List>();

	/* get the list of the verification failures from the current test result
	  @return return a list of the verification failures */
	@SuppressWarnings("rawtypes")
	public static List getVerificationFailures()
	{
		List verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList() : verificationFailures;
	}

	/* add verification failure to current test Result. @param e - Throwable type */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addVerificationFailure(Throwable e)
	{
		List verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
	}

}
