package com.apps.datadrivers;

import org.testng.annotations.DataProvider;

import com.apps.utils.ExcelUtils;

public class TestData {

	@DataProvider(name = "FlightBooking",parallel=false)
	  public static Object[][] flightBooking() {
	        return new Object[][] { 
	        	{ "Bang", "Delhi","23" }, 
	        	{ "Chennai", "Delhi","Any Available" },
	        	{ "Delhi", "Bangalore","18" }
	        };
	  }
	
	
	@DataProvider(name = "GoogleData",parallel=true)
	  public static Object[][] trailData() throws Exception {
	        return ExcelUtils.getExcelAsObjects("google");
	   }
	
	@DataProvider(name = "EmiratesData",parallel=true)
	  public static Object[][] emiratesData() throws Exception {
	        return ExcelUtils.getExcelAsObjects("emirates");
	   }

}
