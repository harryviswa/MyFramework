package com.apps.trial.tests;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.apps.base.CommonTestActions;
import com.apps.listeners.GenericListener;
import com.apps.trail.pages.TrailHomePage;

import io.qameta.allure.Description;

@Listeners( { GenericListener.class } )
public class TrailTest extends CommonTestActions {

    @Test
    @Description("Select the search flight from homepage")
    public void searchFromHomePage() {
    	launch("https://www.emirates.com/");
    	TrailHomePage hp=new TrailHomePage();
    	hp.clickSearchFlight();
    }

	
}
