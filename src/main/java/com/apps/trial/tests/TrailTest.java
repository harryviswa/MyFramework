package com.apps.trial.tests;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.apps.base.CommonTestActions;
import com.apps.listeners.GenericListener;
import com.apps.trail.pages.EmiratesBooking;
import com.apps.trail.pages.TrailHomePage;
import com.apps.trail.pages.TrialGoogle;

import io.qameta.allure.Description;

@Listeners( { GenericListener.class } )
public class TrailTest extends CommonTestActions {

    @Test(testName = "Emirates home page search", enabled=false)
    @Description("Select the search flight from homepage")
    public void searchFromHomePage() {
    	tcm.setTestcaseNameInReports("Emirates home page menu selection");
    	launch("https://www.emirates.com/");
    	TrailHomePage hp=new TrailHomePage();
    	hp.clickSearchFlight();
    }
    
    
    @Test(testName = "Google home page search", enabled=false)
    @Description("Search google with some text")
    public void searchGoogle() {
    	tcm.setTestcaseNameInReports("Google home page search");
    	launch("https://www.google.com/");
    	TrialGoogle hp=new TrialGoogle();
    	hp.googleSearch();
    }
    
    
    @Test(testName = "Emirates home page custom search")
    @Description("Select the search flight from homepage")
    public void searchEmiratesWithData() {
    	tcm.setTestcaseNameInReports("Emirates home page custom search");
    	launch("https://www.emirates.com/");
    	EmiratesBooking book=new EmiratesBooking();
    	book.enterBookingDetails("MAA","DXB","2272023","2282023",2,1,1);
    }
	
}
