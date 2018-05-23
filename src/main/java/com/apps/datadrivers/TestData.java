package com.apps.datadrivers;

import org.testng.annotations.DataProvider;

public class TestData {


	@DataProvider(name = "HotelBooking",parallel=true)
	  public static Object[][] hotelBooking() {
	        return new Object[][] { 
	        	{ "Indiranagar, Bangalore","Any available","Any available" }, 
	        	{ "Indiranagar, Bangalore","24","27" }
	        };
	  }
	
	
	@DataProvider(name = "FlightBooking",parallel=true)
	  public static Object[][] flightBooking() {
	        return new Object[][] { 
	        	{ "Bang", "Delhi","23" }, 
	        	{ "Chennai", "Delhi","Any Available" },
	        	{ "Delhi", "Bangalore","18" }
	        };
	  }

}
