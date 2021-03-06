package shopizer.PageSteps;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import shopizer.PageObjects.ContactPage;
import shopizer.PageObjects.ProductInfoPage;
import shopizer.businessFunctions.AppFunctions;
import shopizer.seleniumCommonFunctions.Config;
import shopizer.seleniumCommonFunctions.SeleniumCommon;
import shopizer.utility.ExceptionHandler;

public class ContactPageTest 
{
	ContactPage cp;

	SeleniumCommon selenium;
	Config config;

	AppFunctions app;
	ProductInfoPage  prodInfo;
	
	@BeforeTest
	public void setUp() throws Exception
	{
		selenium= new SeleniumCommon();
		config=Config.getInstance();
		selenium.openURL(config.getBaseURL());
		app=new AppFunctions(selenium);
		app.doLogin(config.getDefaultUserName(), config.getDefaultPasword());
		cp= new ContactPage(selenium);
		prodInfo= new ProductInfoPage(selenium);
	}
	@Test
	public void TC_001_Verify_That_UserCan_sent_query_on_ContactUs_Page() throws InterruptedException
	{
		try {
			app.selectLinkFromFooterSection("Contact us");
			
			//Thread.sleep(10000);
			app.closeAlert();
			
			String actualMsg=cp.getMessageSent();
			Assert.assertEquals(actualMsg, "Your message has been sent");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ExceptionHandler.addVerificationFailure(e);
		}
		
	}
	@AfterTest
	public void closeBrowser() throws InterruptedException
	{
		Thread.sleep(5000);
		selenium.closeWebDriver();
	}
}
