package com.qa.BaseClass;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.ios.IOSDriver;

public class BaseTest {
  protected static AppiumDriver driver;  // we made it static coz when we'll run the TestNG file, it will 1st come to TESTCLASS > Since it extends BASE class, it will come to BASE class and will initialize Drive>  then it will go back to TESTCLASS  BEFORE method and will initilize loginPage class where its again extending BASECLASS > It will again go to base class and trying to initializing UI elements and driver become null.
  protected static Properties props;   // This is created to load Properties files while initializing the driver.
  protected static HashMap<String, String> strings = new HashMap<String, String>() ;                  // we're initializing it here since we'll be using HasMap STRINGS all our automation. This was added because we added method to read "ExpectedResult" xml file in UTILS class for storing our expected results data in that xml.
  protected static String platform;  // We've created it for method "getText" which is used to get the value of the attribute
  
  
  InputStream stringsis;   // doing it for expectedResults XML file
  InputStream inputStream;
  TestUtils utils;        // Instance created of UTILS class
  
  
  public BaseTest() {
	    
	  
  }
  
  
  
   // IMPORTANT - If you want to run ANDROID or IOS. You need to update that in testNG xml file and update value of the key "platformName, deviceName and UDID"
   
  @Parameters({"platformName", "deviceName", "platformVersion","udid" , "emulator"})   // We're using CAPABILITIES mentioned in testNG XML hence using @Parameter
  @BeforeTest
  public void beforeTest(String platformName ,String deviceName, String platformVersion  , String udid , String emulator   ) throws Exception {   // Initialize driver in this method so that driver is available for all TEST classes. We just need to initiate it once and install app once since all test cases will be executed on 1 device only. In SELENIUM we used to initialize it @BefireMethod level coz in there we initiated driver after every test case.,
	  URL url;
	   platform= platformName;   // To get the platformName which we'll get from TestNG. Its done for the method "getText" created below
	   try {
		  props = new Properties();   // Created Properties Object which will be used to initlaize the Config properties file.
		  String propFileName= "config.properties";  //  complete file path is not  needed since the files is in classpath [ie src/main/resources]		 		  
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);     //Since we created CONFIG file inside src/test/resouces, we can use "Input stream" to load the Config File. 
		  props.load(inputStream);  // We're loading Properies file here. Now inotrder to get properties, all we need to do is use "getProperty()" method
		  
		  String xmlFileName = "ExpectedResultsData/expetedResults.xml";   // saving our ExpectedResusults xml file
		  stringsis = getClass().getClassLoader().getSystemResourceAsStream(xmlFileName);   // Read the expectedResults xml file as InputStream.
		  utils = new TestUtils();
		  strings = utils.parseStringXML(stringsis);  // This will return HASHMAP having our XML key & Pair. So Now we can use strings object in our test class but we need to close both the InputStreams "inputStream and 'stringsis' by using Finally block below in CATCH Block
		  
		  DesiredCapabilities caps = new DesiredCapabilities();                  // There is another way to set the capabilities which is using "OPTIONS" Class
			
		  /*
		    caps.setCapability("platformName", "Android");   // These 3 capabilities will be mentioned in testNG XML file
			caps.setCapability("deviceName", "Nokia5.4");
			caps.setCapability("UDID", "PD21BDD464038424");  */
			
			caps.setCapability("platformName",platformName );   
			caps.setCapability("deviceName",deviceName );
			
			
			switch(platformName) {   // Using Switch Case fro Platform ie Android/IOS             
			
			case "Android":  
				//caps.setCapability("automationName", "UiAutomator2");          // Automation name, App Activity, App Package and Appium Server are global config parameters so we need them in CONFIG FILE
				caps.setCapability("automationName", props.getProperty("androidAutomationName"));  // using Properties file method to use Capability stored in properties File
				
				
				//caps.setCapability("appPackage", "com.swaglabsmobileapp");    // Its SWAG LABS app
				caps.setCapability("appPackage", props.getProperty("androidAppPackage"));   
				
				//caps.setCapability("appActivity", "com.swaglabsmobileapp.SplashActivity");  // Its SWAG LABS app
				caps.setCapability("appActivity", props.getProperty("androidAppActivity"));  
				
				
				
				
				if(emulator.equalsIgnoreCase("true")) {  // If our device is REAL, it won't use this capability and if it is then it will run this emulator. it depends on the value of "TRUE" or "FALSE" in testNG xml key <parameter name= "emulator" value ="false"/ which i will need to update.
					caps.setCapability("appActivity", platformVersion); 
					caps.setCapability("avd", deviceName);  // used this if we have EMULTOR for ANDROID
					
				}
				else {
					
					caps.setCapability("udid", udid);  // If its not EMULTAOR we use this for REAL device . Appium will use UDID if its real, otherwise PLATFORMVERSION if its  emulator . Later its found UDID can be used for both emulator and real device and is always preferred over platform version
				}
				
				// URL appUrl1 = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));   // // Its a way to get complete file path of app. We do that by using "GetResource" Method which will return URL object and then we can pass it as Capability value in next line. This method basically will get the complete path of SRC/test/Resources Package and will append the Relative path of app given in Config file. BUT IT WOn't work and will gove NULL as the value because of using getClassLoader() method.
				String androidAppUrl = getClass().getResource(props.getProperty("androidAppLocation")).getFile();
				
				System.out.println("Url of the app location is:" + androidAppUrl);  // THIS WILL SHOW NULL because of using "classLoader()' method above
				
				
				caps.setCapability("app", androidAppUrl);   // This is the URLwhere apk file is located so that App is installed whenever driver is initalized.
				
				
				//URL url = new URL("http://0.0.0.0:4723/");
			    url = new URL(props.getProperty("appiumURL"));   // variable created above TRY block
				
			
				driver = new AppiumDriver(url, caps);
				break;
				
			case "iOS":
				//caps.setCapability("automationName", "UiAutomator2");          // Automation name, App Activity, App Package and Appium Server are global config parameters so we need them in CONFIG FILE
				caps.setCapability("automationName", props.getProperty("iOSAutomationName"));  // using Properties file method to use Capability stored in properties File
				caps.setCapability("platformVersion", platformVersion);
					
				// URL appUrl1 = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));   // // Its a way to get complete file path of app. We do that by using "GetResource" Method which will return URL object and then we can pass it as Capability value in next line. This method basically will get the complete path of SRC/test/Resources Package and will append the Relative path of app given in Config file. BUT IT WOn't work and will gove NULL as the value because of using getClassLoader() method.
				String iOSAppUrl = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
				
				System.out.println("Url of the app location is:" + iOSAppUrl);  // THIS WILL SHOW NULL because of using "classLoader()' method above
				
				caps.setCapability("bundleId", props.getProperty("iOSBundleId"));  // USED in IOS if APP IS ALREADY INSTALLED
				//caps.setCapability("app", iOSAppUrl);   // This is the URL where apk file is located so that App is installed whenever driver is initalized. ITS USED TO INSTALL THE APP from the given Location.
				
				
				//URL url = new URL("http://0.0.0.0:4723/");
				url = new URL(props.getProperty("appiumURL"));
				driver = new IOSDriver(url, caps);
				break;	
				
				
				default:
					throw new Exception("Inavlid Platform"  + platformName);   // It will throw an exception if platform name is mentioned dfferent than IOS or Android
					
					
			}
			
			
			
			//String sessionId = driver.getSessionId().toString();
			//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));    //  We will use EXPLICITWAITS and not use this
	  }
	 
		  
	
	  
	  
	  catch(Exception e){
		 e.printStackTrace();
		 throw e;   // If driver initialization fails, the program should immediately exit and report the error, preventing further execution.
	  }
	finally {      // we're closing both the InputStreams "inputStream and 'stringsis' by using Finally block which we used above in TRY  block. Now we go to TEST class and replace all Expected Results with our STRINGS used in XML of Expected results.
		if(inputStream !=null) {    
			inputStream.close();
			
		}
		if (stringsis !=null) {
			stringsis.close();
		}
	}
  }
		  
  
  // CREATING METHOD which will be used by DRIVER all around our cases:-
		  
  public void waitForVisibility(WebElement e) {
  utils = new TestUtils();  
  WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(utils.WAIT)); //  Here we are using DYNAMIC WAIT which is mentioned in ULTILS class
  wait.until(ExpectedConditions.visibilityOf(e));  
  }
  
  public void clear (WebElement e){
	  waitForVisibility(e);  // method for clearing the field. 
	  e.clear();
  }
  
  
  
  public void click (WebElement e){
	  waitForVisibility(e);  // calling wait for visibility of element before we click it
	  e.click();
  }
	  
  public void sendKeys (WebElement e, String txt){
	  waitForVisibility(e);  // calling wait for visibility of element before we click it
	  e.sendKeys(txt);
  }
  
	
	  public String getAttribute(WebElement e, String attribute) {
	  waitForVisibility(e); // calling wait for visibility of element before we click it
	   return e.getAttribute(attribute); }
	 
  
  public String getText(WebElement e) {    // Creating this method so that we can get the value of attribute in both ANDROID and IOS . We created this as an upgrade for method "getAttribute" above
	switch(platform) {
	case "Android":
		return getAttribute(e, "text" );
	
		
	case "iOS":
	   return getAttribute(e, "label");
	
	}
	 return null; 
	  
  }
  
  public void CloseApp() {    // creating a CLOSE APP method so that we can use this method after every TEST class is run
	  
	 
	  // ((InteractsWithApps) driver).CloseApp();  "driver.closeApp() and driver.launchApp()" method are now depricated so we need to cast/.
	  
	  
	  
	/*switch(getPlatform())
	  {
	  case "Android":
		  
		  ((InteractsWithApps) getDriver()).terminateApp(getProps().getProperty("androidAppPackage"));
		  break;
		  
	  case "iOS":
		  
		  ((InteractsWithApps) getDriver()).terminateApp(getProps().getProperty("iOSBundleId"));
	  }
  }
  
  
  public void LaunchApp() {
	  
	  switch(getPlatform())
	  {
	  case "Android":
		  
		  ((InteractsWithApps) getDriver()).activateApp(getProps().getProperty("androidAppPackage"));
		  break;
		  
	  case "iOS":
		  
		  ((InteractsWithApps) getDriver()).activateApp(getProps().getProperty("iOSBundleId"));
	  }*/
  }
	  
 
  
  
 

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }
 


}
