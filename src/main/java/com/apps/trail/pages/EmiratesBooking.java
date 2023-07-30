package com.apps.trail.pages;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.apps.base.CommonTestActions;

public class EmiratesBooking  extends CommonTestActions {
	
	@FindBy(xpath="//div[@id='panel0']/descendant::input[@name='Departure airport']")
	WebElement input_departure;
	
	@FindBy(xpath="//div[@id='panel0']/descendant::input[@name='Arrival airport']")
	WebElement input_arrival;
	
	@FindBy(xpath="//label[@class='checkbox one-way']/input")
	WebElement chkbox_oneWay;
	
	@FindBy(xpath="//button[@type='submit']/span[text()='Search flights']")
	WebElement btn_searchFlight;
	
	@FindBy(xpath="//span[text()='Returning' and not(contains(@class,'hidden'))]")
	WebElement lbl_ReturningText;
	
	
	public EmiratesBooking() {
		PageFactory.initElements(new AjaxElementLocatorFactory(getDriver(),40), this);
		pageLoadWait();
		consoleOutput("Landed on Homepage");
		optionalClick("//button[text()='Accept']", "Accept Terms");
	}
	
	public void enterBookingDetails(LinkedHashMap<String,String> data) {
		typeAndSelect(input_departure, data.get("From"), "select value"); 
		typeAndSelect(input_arrival,data.get("To"), "select value"); 
		Calendar dt=Calendar.getInstance();
		dt.add(Calendar.MONTH, 1);
		int dti=dt.get(Calendar.MONTH);
		String returnDate=data.get("ReturnDate").replace("X", ""+dti);
		click("//td[@data-string='"+data.get("FromDate").replace("X", ""+dti)+"']/button","Select from Date");
		if(returnDate.equals("") && isWebElementPresent(lbl_ReturningText,2))
			click(chkbox_oneWay, "OneWay");
		else
			click("//td[@data-string='"+returnDate+"']/button","Select return Date");
		
		String passenger="//div[@data-channel='passenger' and @data-type='XXX']/button[@aria-disabled='false']";
		for(int i=1;i<Integer.parseInt(data.get("Adults"));i++) click(passenger.replace("XXX","adults"),"Adults");
		for(int i=0;i<Integer.parseInt(data.get("Child"));i++) click(passenger.replace("XXX","children"),"Children");
		for(int i=0;i<Integer.parseInt(data.get("Infants"));i++) click(passenger.replace("XXX","infants"),"Infants");

		click(btn_searchFlight,"Search button");
		assertStep(isWebElementPresent("//h1[contains(text(),'Make a booking')]",60), "Search Result Validation");
	}
	
	public void selectBookHoliday() {
		String pwindow=getDriver().getWindowHandle();
		moveToElementAndClick("//span[text()='Book a holiday  ']/parent::a","Book a holiday link");
		getDriver().getWindowHandles().forEach(x->{
		 if(!x.equals(pwindow)) getDriver().switchTo().window(x);
		});
		assertStep(isWebElementPresent("//span[text()='Search Holidays']"),"Landing page validation");
		getDriver().close();
		getDriver().switchTo().window(pwindow);
	}
	
}
