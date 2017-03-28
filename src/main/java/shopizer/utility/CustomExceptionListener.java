package shopizer.utility;

import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;

/**
 * A listener that gets invoked before and after a method is invoked by TestNG. This listener will only be invoked for
 * configuration and test methods. It implements {@link IInvokedMethodListener}.
 * 
 * @author narendra , JAN 2 , 2014
 */
public class CustomExceptionListener implements IInvokedMethodListener
{
	/**
	 * invoked after each configuration and test methods
	 */
	@SuppressWarnings("unchecked")
	public void afterInvocation(IInvokedMethod method, ITestResult result)
	{
		Reporter.setCurrentTestResult(result);
		
		
		if (method.isTestMethod())
		{
			List<Throwable> verificationFailures = ExceptionHandler.getVerificationFailures();
			// if there are verification failures...
			if (verificationFailures.size() != 0)
			{
				// set the test to failed
				result.setStatus(ITestResult.FAILURE);
				// if there is an assertion failure add it to
				// verificationFailures
				if (result.getThrowable() != null)
				{
					verificationFailures.add(result.getThrowable());
				}
				int size = verificationFailures.size();
				// if there's only one failure just set that
				if (size == 1)
				{
					result.setThrowable(verificationFailures.get(0));
				}
				else
				{
					// create a failure message with all failures and stack
					// traces (except last failure)
					StringBuffer failureMessage = new StringBuffer("Multiple failures (").append(size).append("):nn");
					for (int i = 0; i < size - 1; i++)
					{
						failureMessage.append("Failure ").append(i + 1).append(" of ").append(size).append(":n");
						Throwable t = verificationFailures.get(i);
						String fullStackTrace = Utils.stackTrace(t, false)[1];
						failureMessage.append(fullStackTrace).append("nn");
					}
					// final failure
					Throwable last = verificationFailures.get(size - 1);
					failureMessage.append("Failure ").append(size).append(" of ").append(size).append(":n");
					failureMessage.append(last.toString());
					// set merged throwable
					Throwable merged = new Throwable(failureMessage.toString());
					merged.setStackTrace(last.getStackTrace());
					result.setThrowable(merged);
				}
			}
		}
	}

	/**
	 * invoked before each configuration and test methods
	 */
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult)
	{
	}
}