package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager; //Log4j
import org.apache.logging.log4j.Logger; //Log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	public static WebDriver driver;
	public Logger logger; //Log4j
	public Properties p;
	
	@BeforeClass(groups= {"Sanity", "Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws InterruptedException, IOException
	{
		//Loading config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p= new Properties();
		p.load(file);
		
		
		logger=LogManager.getLogger(this.getClass());
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilties=new DesiredCapabilities();
			//os
			if(os.equalsIgnoreCase("Mac"))
			{
				capabilties.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("Windows"))
			{
				capabilties.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				capabilties.setPlatform(Platform.LINUX);
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			
			//browser
			switch(br.toLowerCase())
			{
			case "chrome" :capabilties.setBrowserName("Chrome"); break;
			case "edge" :capabilties.setBrowserName("MicrosoftEdge"); break;
			case "firefox" :capabilties.setBrowserName("firefox"); break;
			default: System.out.println("No matching browser"); return;
			}
			
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilties);
			//driver=new RemoteWebDriver(new URL("http://192.168.1.33:4444/ui/#/sessions"),capabilties);
			
								
		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" : driver = new ChromeDriver(); break;
			case "edge" : driver = new EdgeDriver(); break;
			case "firefox" : driver = new FirefoxDriver(); break;
			default : System.out.println("Invalid browser name...."); return;
			}
		}
		
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL")); //reading url from properties file
		Thread.sleep(3000);
		driver.manage().window().maximize();
		
	}
	
	@AfterClass(groups= {"Sanity", "Regression", "Master", "datadriven"})
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomeString()
	{
		String generatedString=RandomStringUtils.randomAlphabetic(6); //predefined class in java
		return generatedString;
	}
	
	public String randomeNumber()
	{
		String generatedNumber=RandomStringUtils.randomNumeric(10); //predefined class in java
		return generatedNumber;
	}
	
	public String randomeAlphaNumeric()
	{
		String generatedString=RandomStringUtils.randomAlphabetic(3);
		String generatedNumber=RandomStringUtils.randomNumeric(3); 
		return(generatedString + generatedNumber);
	}
	
	public String captureScreen(String tname) 
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		
		TakesScreenshot TakesScreenshot=(TakesScreenshot) driver;
		File sourceFile = TakesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String TargetFilePath = System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File TargetFile = new File(TargetFilePath);
		
		sourceFile.renameTo(TargetFile);
		
		return TargetFilePath;
		
		
				
		
	}


}