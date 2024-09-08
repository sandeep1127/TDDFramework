package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseClass.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductsPage extends BaseTest{

	// For Defining the UI elements, we use @AndroidFindBy for android and @iOSXCUITFindBy for IOS by PageFactopry Class
	
	// UI Elements
	
	@AndroidFindBy (xpath = "//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]/preceding-sibling::android.view.ViewGroup/an")  // Please check this XPATh
	@iOSXCUITFindBy(xpath="//XCUITElementTypeOther{@name=\"test-Toggle\"]/parent::*[1]/preceding-sibling::*[1]")   
	private WebElement productTitleTxt;
	
	
	// Create
	
	
	
	
	public String getTitle() {   // 
		
		return getText(productTitleTxt);
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
