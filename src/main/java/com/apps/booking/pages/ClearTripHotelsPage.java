package com.apps.booking.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.apps.base.CommonTestActions;

public class ClearTripHotelsPage extends CommonTestActions {

	@FindBy(id = "Tags")
    private WebElement localityTextBox;

    @FindBy(id = "SearchHotelsButton")
    private WebElement searchButton;

    @FindBy(id = "travellersOnhome")
    private WebElement travellerSelection;
    
    @FindBy(xpath="//input[@title='Check-in date']")
    WebElement cal_CheckInDate;
    
    @FindBy(xpath="//input[@title='Check-out date']")
    WebElement cal_CheckOutDate;
	
    @FindBy(className="searchSummary")
	WebElement lbl_Summary;
    
	public ClearTripHotelsPage() {
		PageFactory.initElements(getDriver(), this);
		pageLoadWait();
		consoleOutput("YOU ARE CURRENTLY IN CLEAR TRIP HOTELS PAGE");
	}

	public void searchHotels(String strLocalityToSearch,String strCheckInDate,String strCheckOutDate) {
		typeAndSelect(localityTextBox,strLocalityToSearch,"Locality Search");
		selectByVisibleText(travellerSelection,"1 room, 2 adults","Traveller Info");
		selectDate(cal_CheckInDate,strCheckInDate,"Check in Date");
		selectDate(cal_CheckOutDate,strCheckOutDate,"Check Out Date");
        click(searchButton,"Search");
        if(!getText(lbl_Summary).equals(""))
        	consoleOutput("Search Hotel Testcase Successfully Executed");
        else
        	throwError("Search Hotel Testcase Failed");
        
	}
}
