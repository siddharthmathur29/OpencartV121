package testCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("******Starting TC001_TC001_AccountRegistrationTest ***");
		
		try
		{
		HomePage hp = new HomePage(driver);
		Thread.sleep(5000);
		hp.clickMyAccount();
		logger.info("Clicked on MYAccount Link");
		Thread.sleep(5000);
		hp.clickRegister();
		logger.info("Clicked on Register Link");
		
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details");
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString() + "@gmail.com");
		regpage.setPhone(randomeNumber());
		
		String password=randomeAlphaNumeric();
		
		regpage.setPassword(password);
		regpage.setConfPassword(password);
		
		Thread.sleep(3000);
		regpage.setPrivacyPolicy();
		Thread.sleep(5000);
		regpage.clickContinue();
		
		logger.info("Validating expected message");
		String confmsg=regpage.getConfirmationMsg();
		
		if(confmsg.equals("Congratulations! Your new account has been successfully created!"))
		{
			AssertJUnit.assertTrue(true);
		}
		else
		{
			logger.error("Test failed...");
			logger.debug("Debug logs..");
			 AssertJUnit.assertTrue(false);
		}

		}
		catch(Exception e)
		{
			
			AssertJUnit.fail();
		}
		
		logger.info("******Finished TC001_TC001_AccountRegistrationTest ***");
	}
	
	

}
