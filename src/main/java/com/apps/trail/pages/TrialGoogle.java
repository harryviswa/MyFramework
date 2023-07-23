package com.apps.trail.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.apps.base.CommonTestActions;
import io.qameta.allure.Step;

public class TrialGoogle extends CommonTestActions {

	@FindBys(@FindBy(xpath="//input[@value='Google Search']"))
	List<WebElement> btn_search;
	
	@FindBys(@FindBy(xpath="//img[@alt='Google']"))
	List<WebElement> lst_logo;
	
	@FindBy(xpath="//textarea[@title='Search']")
	WebElement txt_search;
	
	@FindBys(@FindBy(xpath="//h3/.."))
	List<WebElement> lst_searchResults;
	
	public TrialGoogle() {
		PageFactory.initElements(new AjaxElementLocatorFactory(getDriver(),40), this);
		pageLoadWait();
		consoleOutput("Landed on Homepage");
	}

	@Step("Search in google search bar")
	public void googleSearch(String searchterm) {
		assertStep(lst_logo.size()>0,"Home page validation");
		optionalClick("//a[text()='English']","Switch Language");
		type(txt_search,searchterm, "Google search text: "+searchterm);
		click(btn_search.get(0), "Google Search");
		consoleOutput("Total Search Results: "+lst_searchResults.size());
		//assertStep(lst_searchResults.get(0).getText().contains("Hariharan Viswanathan"),"Search list value");
		click(lst_searchResults.get(0),"Click on the first link");
		captureScreenshot();
	}

}
