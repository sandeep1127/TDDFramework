package com.qa.testClasses;

import org.testng.annotations.Test;

import com.qa.BaseClass.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest{          // 1st -> We need to do this to initialize our Driver. Doing this, when TestNG will execute LoginPage Test class it will 1st go to BASE class and will run @beforeMethod and will initialize driver
  
	// 2nd -> Initialize Login Page and then PRODUCTS page at class level
	LoginPage loginPage;
	ProductsPage productspage;
	InputStream datais;     // We created this to use JSON Test DATA file we created in src/test/Resources . This is called adding ABSTRACTION layer to our Test Data
	JSONObject loginUsers; // we're creating JSON Object
	
	

	
 
  
  @BeforeClass
  public void beforeClass() throws IOException {
	  
	  try{
		  String dataFileName = "data/loginUsers.json";     // This is the path of the DATA file we created to read the JSON test data of users credentials
			 datais= getClass().getClassLoader().getResourceAsStream(dataFileName);  // we're reading the JSON file via InputSTream Method's
			 JSONTokener tokener = new JSONTokener(datais);   // Now we need to create a JSON tokkener and then we need to pass the InputStream to the Tokenner
			 loginUsers = new JSONObject(tokener);									// creating object of JSON amd pass the Tokenner to  JSON Object .   
	  }
	  catch(Exception e) {
		e.printStackTrace();
		throw e; // If we don;t write this, then in case Exception occurs testNG will think that since we are catching them  it will not throw it and will continue with the cases. This line will throw the exception..
	  }
	 
	  finally {
		  if (datais!=null) {    // doing a null check
			  datais.close();   // we're closing the InputStream when its not null after making us of it
		  }
	  }
	 
	 // Now best practise is to close the InputStream so we use TRY CATCH block and we'll close it using FINALLY block . AFter this we will extract the TEST DATA in TEST CLASSES 
  }

  @AfterClass
  public void afterClass() {
  }


  @BeforeMethod
  public void beforeMethod(Method m) {                      // 4th ->If we want to print METHOD NAME in the console we can use Before Method and METHOD class for this.
	  loginPage = new LoginPage();                         // 3rd -> Since every test case will need to login first so lets initialize Loginpage in Before method.  Now you can create all Tets cases
  System.out.println("\n" + "****** Starting TestMethod : " + m.getName()+ "******" + "\n");   // This will print the method name which is currently executing
  }

  @AfterMethod
  public void afterMethod() {
  }

   
  
  @Test
  public void invalidUSerName() {
	// loginPage.enterUserName("invalidusername") ;   
	  loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username")); // we're extracting TEST DATA from JSON File. Using JSON object and then passing the key 
	 loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
	 loginPage.pressLoginBtn();
	 
	 String actualErrText = loginPage.getErrTxt();
	 String expectedErrText = "Username and password do not match any user in this service.";
	 
	  Assert.assertEquals(actualErrText, expectedErrText);
  }
  
  
  
  
  
  /*
   * @Test
  
  public void invalidPassword() {               
  try {                                              // We used exceptional handelling coz it would display the exceptions in the CONSOLE by doing this, otherwise it won't. But problem here is that even if test case fails TESTNG will pass it coz TESNG won't get to know that exception was thrown. For this we need to add ASSERT statement for letting testNG know
	    loginPage.enterUserName("standard_user") ;
		 loginPage.enterPassword("invalidpassword");   // IMP : Instead of try/catch block we'll be using testNG LISTENER class . read line no 91
		 loginPage.pressLoginBtn();
		 
		 String actualErrText = loginPage.getErrTxt();
		 String expectedErrText = "Username and password do not match any user in this service.";
		 System.out.println("actual error text- " + actualErrText + "\n" + "expected error text - " +expectedErrText );
		  Assert.assertEquals(actualErrText, expectedErrText);  
        }
  
  catch(Exception e) {
	  StringWriter sw = new StringWriter();   // If this line was not added, TestNG in case of exception will simply tell that line no 84 has error even if error was somewhere else and  it won't display detail of exception in results. Adding this line would
	  PrintWriter pw = new PrintWriter(sw);
	                         
	  
	  e.printStackTrace(pw); // Instead of printing it to console we will print it to PrintWriter, thats why gave argument 'pw'
	  System.out.println(sw.toString()); // This command will print the complete stack trace to console 
	  Assert.fail(sw.toString()); // By this line we are letting TESTNG know that Test case has failed in case it fails and using "sw" we are printing print trace to TestNG as well.
	  
	  // PROBLEM HERE> We'll have to add complete code from line 83 to 89 in each test case which is not good OR we can create a method in UTILS . For such cases best is to use TestNG LISTERNERS where we don't need TRY CATCH block. For this we create different package LISTNERS > TestListner
	   
        }
  */
  
   // This below Method was updated from top one. So read above code 1st. we removed TRY CATCH from above and use LISTNERS instead.
   
  
  public void invalidPassword() {  
     loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username")) ;
	 loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));   // IMP : Instead of try/catch block we'll be using testNG LISTNER class . read line no 91
	 loginPage.pressLoginBtn();
	 
	 String actualErrText = loginPage.getErrTxt();
	 //String expectedErrText = "Username and password do not match any user in this service.";        // Now this is a Static hardcoded text which shouldn't be used here. Instead we can store all such EXPECTED RESULT static texts in a XML file which we have saved in EXPECTEDRESULTS folder by creating XML file. We used DOM to read XML file  which is defined in ULTIS class
	 String expectedErrText = strings.get("err_invalid_username_or_password");  // Here instead of writing static text like in above line, we are taking using "Key" which will give 'value' from ExpectedResults XML by using the object 'Strings' which we created in BaseClass
	 
	 
	 System.out.println("actual error text- " + actualErrText + "\n" + "expected error text - " +expectedErrText );
	  Assert.assertEquals(actualErrText, expectedErrText);  
  
  
  
  
  
  
  }
  
  
  
  @Test
  public void successfulLogin() {
	 loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("username")) ;
	 loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
	 productspage= loginPage.pressLoginBtn();    // Since this will navigate to PRODUCTS page so we are returning Products Pageand we're initializing Products here itself
	 
	 String actualProductTitle = productspage.getTitle();
	// String expetedProductTitle = "PRODUCTS";  // Now this is a Static hardcoded text which shouldn't be used here. Instead we can store all such EXPECTED RESULT static texts in a XML file whichw e have saved in EXPECTEDRESULTS folder. We'll use DOM approach mentioned in "parseStringXML" method to read XML file  which is defined in UTILS class
	 String expetedProductTitle = strings.get("product_title");  // Here instead of writing like above line, we are taking data""Key" from ExpectedResults XML by sing the object Strings which we created in BaseClass
	 
	 System.out.println("actual title- " + actualProductTitle + "\n" + "expected title - " +expetedProductTitle );
	 Assert.assertEquals(actualProductTitle, expetedProductTitle);
	 
	 
  }
  
  
  
  
  
  
  
  
}
