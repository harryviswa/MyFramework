package com.apps.booking.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.apps.base.CommonTestActions;

public class ClearTripHomePage extends CommonTestActions {

	@FindBy(id="SignIn")
	WebElement btn_SignIn;
	
	@FindBy(linkText="Your trips")
	WebElement lnk_YourTrips;
	
	@FindBy(id="OneWay")
	WebElement opt_OneWay;
	
	@FindBy(id="FromTag")
	WebElement edt_From;
	
	@FindBy(id="ToTag")
	WebElement edt_To;
	
	@FindBy(xpath="//*[@id='ui-id-1']/li/a")
	WebElement lnk_FromAutoComplete;
	
	@FindBy(xpath="//*[@id='ui-id-2']/li/a")
	WebElement lnk_ToAutoComplete;
	
	@FindBy(id="SearchBtn")
	WebElement btn_Search;
	
	@FindBy(className="searchSummary")
	WebElement lbl_Summary;
	
	@FindBy(xpath="//input[@title='Depart date']")
    WebElement calDepartDate;
	
	public ClearTripHomePage() {
		PageFactory.initElements(getDriver(), this);
		pageLoadWait();
		System.out.println("YOU ARE CURRENTLY IN CLEAR TRIP HOME PAGE");
	}

	public void clickSignIn() {
        click(lnk_YourTrips,"Your Trips");
        click(btn_SignIn,"Sign In");
	}

	public void searchFlightAndBook(String strFrom, String strTo, String strDepartDate) {
		
		click(opt_OneWay,"One Way"); 
        typeAndSelect(edt_From,strFrom,"From-Location"); 
        typeAndSelect(edt_To,strTo,"To-Location");
        selectDate(calDepartDate,strDepartDate,"Depart Date");
        click(btn_Search,"Search");
        
        //verify that result appears for the provided journey search
        if(!getText(lbl_Summary).equals(""))
        	consoleOutput("Search Flight Testcase Successfully Executed");
        else
        	throwError("Search Flight Testcase Failed");
	}
	
	public void navigateTo(String strNavigateLinkName) {
		click("//a[text()='"+strNavigateLinkName+"']",strNavigateLinkName);
	}
}
