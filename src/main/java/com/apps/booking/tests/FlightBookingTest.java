package com.apps.booking.tests;
import com.apps.base.CommonTestActions;
import com.apps.booking.pages.ClearTripHomePage;
import org.testng.annotations.Test;

public class FlightBookingTest extends CommonTestActions {

    @Test(dataProviderClass=com.apps.datadrivers.TestData.class,dataProvider="FlightBooking")
    public void testThatResultsAppearForAOneWayJourney(String strFrom, String strTo, String strDepartDate) {
       
    	launch("https://www.cleartrip.com/");
        ClearTripHomePage obj_hp=new ClearTripHomePage();
        obj_hp.searchFlightAndBook(strFrom, strTo,strDepartDate);

    }

}
