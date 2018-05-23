package com.apps.booking.tests;
import com.apps.base.CommonTestActions;
import com.apps.booking.pages.ClearTripHomePage;
import com.apps.booking.pages.ClearTripHotelsPage;
import org.testng.annotations.Test;

public class HotelBookingTest extends CommonTestActions {

    @Test(dataProviderClass=com.apps.datadrivers.TestData.class,dataProvider="HotelBooking")
    public void shouldBeAbleToSearchForHotels(String strLocation,String strFromDate,String strToDate) {

        launch("https://www.cleartrip.com/");
        
        ClearTripHomePage obj_hp=new ClearTripHomePage();
        obj_hp.navigateTo("Hotels");
        
        ClearTripHotelsPage obj_hotel=new ClearTripHotelsPage();
        obj_hotel.searchHotels(strLocation,strFromDate,strToDate);
    }

}
