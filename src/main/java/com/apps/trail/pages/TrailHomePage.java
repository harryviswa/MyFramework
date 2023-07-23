package com.apps.trail.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.apps.base.CommonTestActions;
import io.qameta.allure.Step;

public class TrailHomePage extends CommonTestActions {

	@FindBy(xpath="//a[contains(text(),'BOOK')]")
	WebElement lnk_book;
	
	@FindBys(@FindBy(xpath="//img[@alt='Emirates logo']"))
	List<WebElement> lst_logo;
	
	@FindBy(xpath="//a[contains(string(),'Search flights') and not(contains(@style,'display: none'))]")
	WebElement tab_searchFlight;
	
	public TrailHomePage() {
		PageFactory.initElements(new AjaxElementLocatorFactory(getDriver(),40), this);
		pageLoadWait();
		consoleOutput("Landed on Homepage");
		optionalClick("//button[text()='Accept']", "Accept Terms");
	}

	@Step("Click on search flight")
	public void clickSearchFlight() {
		assertStep(lst_logo.size()>0,"Home page validation");
		assertStep(tab_searchFlight.isDisplayed(),"Landing page element validations");
		click(lnk_book, "Book");
		moveToElementAndClick("//i[contains(text(),'Search for a flight')]/../..","Select Menu Item");
		assertStep(isWebElementPresent("//h1[(text()='Book a flight')]"),"Header Validation");

	}

}
