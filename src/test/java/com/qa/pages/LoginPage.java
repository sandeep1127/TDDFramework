package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.qa.BaseClass.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BaseTest{

	// For Defining the UI elements, we use @AndroidFindBy provide by PageFactopry Class
	
	// UI Elements
	
	@AndroidFindBy (accessibility = "test-Username")   //For ANDROID , we use "accessibility" when parameter is "accessibility Id" 
	@iOSXCUITFindBy(id="test-Username")                // For IOS, we use "id" when parameter is "accessibility Id" 
	private WebElement usernameTxtfld;                 // common name of the web element for ISO and ANDROID
	
	@AndroidFindBy (accessibility = "test-Password") 
	@iOSXCUITFindBy(id="test-Password")
	private WebElement passwordTxtfld;
	
	@AndroidFindBy (accessibility = "test-LOGIN") 
	@iOSXCUITFindBy(id="test-LOGIN")
	private WebElement loginBtn;
	
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
	@iOSXCUITFindBy(xpath="//XCUIElementTyoeOther[@name=\"test-Error message\"]/child::XCUITElementTypeStaticText")
	 private WebElement errTxt;
	// Create
	
	
	public LoginPage() {
		
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);  // With the version of java client 9.1, now we need to initialize Page factory in every Page Class.	
	}
	
	
	
	
	public LoginPage enterUserName(String username) {   // Method for entering the username
		clear(usernameTxtfld);
		sendKeys(usernameTxtfld, username);
		
		return this;   // Since we are staying on the same page ie Login page and not going to navigating to another page , we will return object of the same class.
	}
	
	
	public LoginPage enterPassword(String password) {   // Method for entering the username
		clear(usernameTxtfld);
		sendKeys(usernameTxtfld, password);
		return this;   // Since we are staying on the same page ie Login page and not going to navigating to another page , we will return object of the same class.
		
	}
	
	
	public ProductsPage pressLoginBtn() {   // Method for clicking login button
	  click(loginBtn);
	  return new ProductsPage();    // After clicking Login button since we are navigating to different page so we'll return object that page  
	
	}
	
	
	public String getErrTxt() {
		
		return getText(errTxt);    // This method we created in BASE CLASS . Here "text" was the name of the attribute  in ANDROID whose value we returned  but in IOS we don't have "text" attribute if you'll check in appium inspector. instead we have "label" atribute which is not supported by "getAttribute" method. So what we can do is , we can use platform name and on tht basis it can work out. SO we'll handle this in BASE CLASS so that it works for all PAGES.
		 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
