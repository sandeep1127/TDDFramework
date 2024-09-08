package com.qa.listeners;

import java.io.PrintWriter;
import java.io.StringWriter;
                                                                                             // 1. CREATED this LISTENER class so that we can use pre written method to get details of Exception if they do occur
import org.testng.ITestListener;                                                              //2. Always call the Listner using testNG XML . 2 ways to do it . 1st = call it through test class via annotation @Listeners   2nd = using testNG XML.   If we used 1st, we'll have to do it for every class but using 2nd we can do it from XML itself which is better
import org.testng.ITestResult;

public class TestListener implements ITestListener{                                 // We're creating this LISTENER Class and implementing ITestListener so that we don't have use TRY/CATCH block in every test case to get the exception detail if one occurs.Annotation @listeneer will be used to use this

	// READ the method "invalidPassword" in green code  inside LOGINPAGE test class to understand this java class
	
 public void onTestFailure(ITestResult result) {   // ITestResult Class > This method will print the Exception details if any test method fails
	 if (result.getThrowable()!=null) {
		 StringWriter sw = new StringWriter();   // If this line was not added, TestNG in case of exception will simply tell that there is error but it won't display detail of exception in results. Adding this line would do it
		 PrintWriter pw = new PrintWriter(sw);
		 result.getThrowable().printStackTrace(pw);  //  using "getThrowable" method we're reading the exception from our test method and printing it to 'PrintWriter'
		 System.out.println(sw.toString()); // Converting the details of exception into String format ie readable format
	 }
	 
 }
	
}
