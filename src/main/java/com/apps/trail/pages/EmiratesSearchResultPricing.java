package com.apps.trail.pages;

import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.apps.base.CommonTestActions;

public class EmiratesSearchResultPricing  extends CommonTestActions {
	
	@FindBy(xpath="//span[@class='summary-curr-only']")
	WebElement lbl_lowestPrice;
	
	@FindBys(@FindBy(xpath="//a[contains(string(),'Price')]"))
	List<WebElement> lst_priceFilter;
	
	@FindBys(@FindBy(xpath="//div[@class='ts-fbr-flight-list-row__header-core']//*[@class='ts-fip__fin-info-col']"))
	List<WebElement> lst_flightDetails;
	
	@FindBys(@FindBy(xpath="//div[@class='ts-fbr-flight-list-row__header-core']//*[@class='ts-fbr-option__price']"))
	List<WebElement> lst_priceDetails;
	
	public EmiratesSearchResultPricing() {
		PageFactory.initElements(new AjaxElementLocatorFactory(getDriver(),40), this);
		pageLoadWait();
		consoleOutput("Landed on Homepage");
		optionalClick("//button[text()='Accept']", "Accept Terms");
	}
	
	public void selectLowestPrice(LinkedHashMap<String,String> data) {
		assertStep(true, "Lowest Price Identified: "+  getText(lbl_lowestPrice));
		moveToElementAndClick(lst_priceFilter.get(0),"Sort by price");
		int[] i= {0};
		lst_flightDetails.forEach(x->{
			getText(x);
			getText(lst_priceDetails.get(i[0]++));
			getText(lst_priceDetails.get(i[0]++));
			getText(lst_priceDetails.get(i[0]++));
		});
	}
	
}
