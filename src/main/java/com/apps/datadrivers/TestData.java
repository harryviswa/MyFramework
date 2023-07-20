package com.apps.datadrivers;

import org.testng.annotations.DataProvider;

import com.apps.utils.ExcelUtils;

public class TestData {


	@DataProvider(name = "HotelBooking",parallel=false)
	  public static Object[][] hotelBooking() {
	        return new Object[][] { 
	        	{ "Indiranagar, Bangalore","Any available","Any available" }, 
	        	{ "Indiranagar, Bangalore","24","27" }
	        };
	  }
	
	
	@DataProvider(name = "FlightBooking",parallel=false)
	  public static Object[][] flightBooking() {
	        return new Object[][] { 
	        	{ "Bang", "Delhi","23" }, 
	        	{ "Chennai", "Delhi","Any Available" },
	        	{ "Delhi", "Bangalore","18" }
	        };
	  }
	
	
	@DataProvider(name = "TrailData",parallel=false)
	  public static Object[][] trailData() throws Exception {
	        return ExcelUtils.getTableArray();
	   }

}
