package com.apps.booking.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.apps.base.CommonTestActions;

public class ClearTripSignInPage extends CommonTestActions {

	@FindBy(id="signInButton")
	WebElement btn_SignIn;
	
	@FindBy(id="errors1")
	WebElement lbl_Errors;
	
	public ClearTripSignInPage() {
		PageFactory.initElements(getDriver(), this);
		pageLoadWait();
		System.out.println("YOU ARE CURRENTLY IN CLEAR TRIP SIGNIN PAGE");
	}

	public void validateInvalidLogin() {
		
		switchToFrame("modal_window");
        
        //Other operations are skipped to validate Invalid Login
        
        click(btn_SignIn,"Sign In");
        
        if(getText(lbl_Errors).contains("There were errors in your submission"))
        	consoleOutput("Testcase was successful");
        else
        	throwError("Sign in page error not found and failed");
	}

}
