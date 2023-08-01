package com.apps.trial.tests;
import java.util.LinkedHashMap;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.apps.base.CommonTestActions;
import com.apps.listeners.GenericListener;
import com.apps.trail.pages.EmiratesBooking;
import com.apps.trail.pages.EmiratesSearchResultPricing;
import com.apps.trail.pages.TrailHomePage;
import com.apps.trail.pages.TrialGoogle;

import io.qameta.allure.Description;

@Listeners( { GenericListener.class } )
public class TrailTest extends CommonTestActions {

    @Test(enabled=false)
    @Description("Select the search flight from homepage")
    public void searchFromHomePage() {
    	tcm.setTestcaseNameInReports("Emirates home page menu selection");
    	launch("https://www.emirates.com/");
    	TrailHomePage hp=new TrailHomePage();
    	hp.clickSearchFlight();
    }
    
    
    @Test(testName = "Google home page search", enabled=false,  dataProvider="GoogleData", dataProviderClass = com.apps.datadrivers.TestData.class)
    @Description("Search google with some text")
    public void searchGoogle(LinkedHashMap<String,String> data) {
    	tcm.setTestcaseNameInReports(data.get("TestcaseName"));
    	launch(data.get("Url"));
    	TrialGoogle hp=new TrialGoogle();
    	hp.googleSearch(data.get("SearchTerms"));
    }
    
    
    @Test(dataProvider="EmiratesData", dataProviderClass = com.apps.datadrivers.TestData.class)
    @Description("Select the search flight from homepage")
    public void searchEmiratesWithData(LinkedHashMap<String,String> data) {
    	tcm.setTestcaseNameInReports("Emirates home page custom search");
    	launch("https://www.emirates.com/");
    	EmiratesBooking book=new EmiratesBooking();
    	book.enterBookingDetails(data);
    	EmiratesSearchResultPricing search=new EmiratesSearchResultPricing();
    	search.selectLowestPrice(data);
    }
    
    @Test(enabled=false)
    @Description("Select the search flight from homepage")
    public void selectAndBookHoliday() {
    	tcm.setTestcaseNameInReports("Emirates Book a holiday");
    	launch("https://www.emirates.com/");
    	EmiratesBooking book=new EmiratesBooking();
    	book.selectBookHoliday();
    }
	
}
