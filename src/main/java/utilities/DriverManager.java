package utilities;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.*;

public class DriverManager
{
	
	public void setUp(String browserName)  
	{
		//Create an instance of FirefoxOptions
		FirefoxOptions options = new FirefoxOptions();
		//Create an instance of ChromeOptions
		ChromeOptions chromeOptions = new ChromeOptions();
		switch (browserName.toLowerCase()) 
		{
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				options.setAcceptInsecureCerts(true);
				options.addPreference("dom.webnotifications.enabled", false);
				options.addPreference("dom.webnotifications.serviceworker.enabled", false);
				options.addPreference("dom.push.enabled", false);
				options.addPreference("geo.enabled", true);
				options.addPreference("geo.provider.use_corelocation", true);
				options.addPreference("geo.prompt.testing", true);
				options.addPreference("geo.prompt.testing.allow", true);
				options.addPreference("permissions.default.camera", 1);
				ThreadManager.setDriver(new FirefoxDriver(options));
				break;

			case "firefox uab":
				WebDriverManager.firefoxdriver().setup();
				options.setHeadless(true);
				options.setAcceptInsecureCerts(true);
				options.addPreference("dom.webnotifications.enabled", false);
				options.addPreference("dom.webnotifications.serviceworker.enabled", false);
				options.addPreference("dom.push.enabled", false);
				options.addPreference("geo.enabled", true);
				options.addPreference("geo.provider.use_corelocation", true);
				options.addPreference("geo.prompt.testing", true);
				options.addPreference("geo.prompt.testing.allow", true);
				options.addPreference("permissions.default.camera", 1);
				options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
				ThreadManager.setDriver(new FirefoxDriver(options));
				break;
	
			case "chrome":
				WebDriverManager.chromedriver().setup();
				//Create a map to store  preferences
				Map<String, Object> prefs = new HashMap<>();
				//add key and value to map as follow to switch off browser notification
				//Pass the argument 1 to allow and 2 to block
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("profile.managed_default_content_settings.geolocation", 1);
				// set ExperimentalOption - prefs
				chromeOptions.setExperimentalOption("prefs", prefs);
				chromeOptions.addArguments("--incognito");
				chromeOptions.addArguments("--disable-gpu");
				chromeOptions.addArguments("use-fake-ui-for-media-stream");
				//Now Pass ChromeOptions instance to ChromeDriver Constructor to initialize chrome driver which will switch off this browser notification on the chrome browser
				ThreadManager.setDriver(new ChromeDriver(chromeOptions));
				break;

			case "safari":
				System.setProperty("webdriver.safari.driver", "src/main/resources/drivers/safaridriver");
				ThreadManager.setDriver(new SafariDriver());
				break;
	
			case "headless chrome":
				WebDriverManager.chromedriver().setup();
				chromeOptions.addArguments(
					"--headless",
					"--window-size=1440x900",
					"--disable-gpu",
					"--disable-extensions",
					"--incognito",
					"--proxy-server='direct://'",
					"--proxy-bypass-list=*"
				);
				chromeOptions.addArguments("use-fake-ui-for-media-stream");
				chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
				ThreadManager.setDriver(new ChromeDriver(chromeOptions));
				break;

			case "mobile chrome":
				WebDriverManager.chromedriver().setup();
				Map<String, String> mobileEmulation = new HashMap<>();
				mobileEmulation.put("deviceName", "Nexus 5");
				chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
				chromeOptions.addArguments("--incognito");
				ThreadManager.setDriver(new ChromeDriver(chromeOptions));
				break;

			case "headless firefox":
				WebDriverManager.firefoxdriver().setup();
				options.setHeadless(true);
				options.setAcceptInsecureCerts(true);
				options.addArguments("window-size=1440,900");
				options.addPreference("dom.webnotifications.enabled", false);
				options.addPreference("dom.webnotifications.serviceworker.enabled", false);
				options.addPreference("dom.push.enabled", false);
				options.addPreference("geo.enabled", true);
				options.addPreference("geo.provider.use_corelocation", true);
				options.addPreference("geo.prompt.testing", true);
				options.addPreference("geo.prompt.testing.allow", true);
				ThreadManager.setDriver(new FirefoxDriver(options));
				break;

			case "ie":
				WebDriverManager.iedriver().setup();
				ThreadManager.setDriver(new InternetExplorerDriver());
				break;
				
			case "edge":
				WebDriverManager.edgedriver().setup();
				ThreadManager.setDriver(new EdgeDriver());
				break;	
				
			default:
				throw new IllegalArgumentException("Please specify valid browser name. Valid browser names are: firefox, chrome,chrome-headless, ie ,edge");

		}
			
		ThreadManager.getDriver().manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		ThreadManager.getDriver().manage().timeouts().pageLoadTimeout(Constants.PAGELOAD_WAIT_DURATION, TimeUnit.SECONDS);
		ThreadManager.getDriver().manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);        
		ThreadManager.getDriver().manage().window().maximize(); 
	 }
	
	public void tearDown()
	{
		if(ThreadManager.getDriver()!=null)
		{
			ThreadManager.getDriver().quit();
		}
	}
}
