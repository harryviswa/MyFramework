package com.apps.booking.tests;
import org.testng.annotations.Test;
import com.apps.base.CommonTestActions;
import com.apps.booking.pages.ClearTripHomePage;
import com.apps.booking.pages.ClearTripSignInPage;

public class SignInTest extends CommonTestActions {

    @Test
    public void shouldThrowAnErrorIfSignInDetailsAreMissing() {
        
    	launch("https://www.cleartrip.com/");
    	
    	ClearTripHomePage obj_hp=new ClearTripHomePage();
    	obj_hp.clickSignIn();
        
        ClearTripSignInPage obj_sip=new ClearTripSignInPage();
        obj_sip.validateInvalidLogin();
    }

	
}
