package shopizer.businessFunctions;

import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import shopizer.seleniumCommonFunctions.Config;
import shopizer.seleniumCommonFunctions.SeleniumCommon;
import shopizer.utility.ReadPropertyFile;

public class AppFunctions   
{
	
	Logger log=Logger.getLogger(AppFunctions.class);
	SeleniumCommon selenium;
	
	ReadPropertyFile  readProps;
	TreeMap<String,String>commLocators;// map to store locator for common Object repository
	TreeMap<String,String>compBooksLocators;// map to store locator for Computer books object repository
	
	TreeMap<String,String>bbpLocators;
	Config config=Config.getInstance();
	
	

	public AppFunctions(SeleniumCommon selenium) 
	{
		this.selenium=selenium;
		readObjects();
	}
	

	/**
	 *  read the web element objects from object repository file
	 */
	private void readObjects() 
	{
		readProps= new ReadPropertyFile();
		commLocators=readProps.getLocatorMap(config.getCommonObjectFile());
		compBooksLocators=readProps.getLocatorMap(config.getComputerBooksObjectFile());
		bbpLocators=readProps.getLocatorMap(config.getBusinessBooksObjectFile());
	}
/**
 * login method to verify login functionality, give username and password
 * 
 * @param defaultUserName - userName
 * @param defaultPasword - Password
 * @return
 * @throws InterruptedException 
 */
	public boolean doLogin(String defaultUserName, String defaultPasword) throws InterruptedException 
	{
		 
		selenium.getElement(commLocators.get("commonObject.SignLink")).click();
		selenium.getElement(commLocators.get("commonObject.LoginUserName")).sendKeys(defaultUserName);
		selenium.getElement(commLocators.get("commonObject.LoginPassword")).sendKeys(defaultPasword);
		selenium.getElement(commLocators.get("commonObject.LoginButton")).click();
		
		Thread.sleep(10000);
		
		boolean bLogin=selenium.getElement(commLocators.get("commonObject.AccountButton")).isDisplayed();
		
		return bLogin;
	}
	
	/**
	 * it selects the given menu .
	 * @param sMenuName- give the name for menu to be selected
	 * return - true or false based on menu selection
	 */
	public boolean selectMenu(String sMenuName) throws Exception
	{
		
			
			switch(sMenuName.toUpperCase())
			{
				case "COMPUTERBOOKS":
					   
					   //selenium.getElement(compBooksLocators.get("cpBooksPage.MenuLink")).click();
					   selenium.doubleClick(compBooksLocators.get("cpBooksPage.MenuLink"));
					   //selenium.getElementByClickable(compBooksLocators.get("cpBooksPage.ComputerBookMenu")).click();
					return selenium.getElement(compBooksLocators.get("cpBooksPage.ComputerBooksMenuHeader")).isDisplayed();
				case "BUSINESSBOOKS":
					   
					   //selenium.getElement(compBooksLocators.get("cpBooksPage.MenuLink")).click();
					   selenium.doubleClick(bbpLocators.get("bbPage.MenuLink"));
					   //selenium.getElementByClickable(compBooksLocators.get("cpBooksPage.ComputerBookMenu")).click();
					return selenium.getElement(bbpLocators.get("bbPage.BusinessMenuHeader")).isDisplayed();
				case "TECHNOLOGYBOOKS":
					selenium.getElementByClickable(compBooksLocators.get("")).click();
					
					return  selenium.getElement(compBooksLocators.get("cpBooksPage.TechnologyBooksMenuHeader")).isDisplayed();
				case "WEBBOOK":
		
					selenium.getElementByClickable(compBooksLocators.get("")).click();
					return  selenium.getElement(compBooksLocators.get("cpBooksPage.WebBooksMenuHeader")).isDisplayed();

				default :
					
					log.error(" Unknown menu type. Please give valid menus name from list[COMPUTERBOOKS,TECHNOLOGYBOOKS,WEBBOOK]");
					return false;
			}
			
	}
	/**
	 * 
	 * @param sDropDownName - Name of the drop down box in you application
	 * @return
	 */
	public WebElement getDropDown(String sDropDownName)
	{
		WebElement eleDropDown=null;
		switch(sDropDownName.toUpperCase())

		{
		case "SORTFILTER":
			eleDropDown=selenium.getElement(compBooksLocators.get("cpBooksPage.SortFilterDropDown"));
			
			break;
			
			default :
				log.error("Unkown drop down value.");
		}
		return eleDropDown;
		
	}
	public boolean selectPublisher(String publisherName)
	{
		boolean bResult=false;
		WebElement eleRoot=selenium.getElement(commLocators.get("commonObjects.PublisherNameList"));
		List<WebElement>lstLinks=selenium.getChildElements(eleRoot, commLocators.get("commonObjects.PublisherNameFindBy"));
		
		for(WebElement link:lstLinks)
		{
			System.out.println(link.getText());
			if(link.getText().contains(publisherName))
			{
				link.click();
				bResult=true;
				break;
			}
		}
		return bResult;
	}
	public boolean selectAllPublisher()
	{
		try {
			WebElement eleRoot=selenium.getElement(commLocators.get("commonObjects.PublisherNameList"));
			List<WebElement>lstLinks=selenium.getChildElements(eleRoot, commLocators.get("commonObjects.PublisherNameFindBy"));
			
			for(WebElement link:lstLinks)
			{
				System.out.println(link.getText());
				
				link.click();
				Thread.sleep(5000);
				
			}
			return true;
		} catch (InterruptedException e) {
			 return false;
		}
	}


	public void selectLinkFromFooterSection(String linkText) {
		// TODO Auto
		List<WebElement>lstEle=selenium.getElements(commLocators.get("commonObject.AllFooterLinks"));
		for(WebElement  ele: lstEle)
		{
			System.out.println(ele.getText());
			if(ele.getText().toLowerCase().contains(linkText.toLowerCase()))
			{
				ele.click();
				break;
			}
		}
	}
	
	public void closeAlert()
	{
		selenium.closeAlert();
	}

}
