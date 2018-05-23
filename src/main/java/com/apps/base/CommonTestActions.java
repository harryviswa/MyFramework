package com.apps.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("serial")
class bookingException extends TimeoutException{
	public bookingException(String strErrorMessage) {
		super(strErrorMessage);
	}
}

public class CommonTestActions extends CommonTestBase {

	final int GLOBAL_SYNC_WAIT_TIME=5;

	public void waitFor(int durationInMilliSeconds) {
		try {
			Thread.sleep(durationInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		}
	}

	public WebElement findWebElement(By byInfo) {
		syncFor(byInfo);
		return getDriver().findElement(byInfo);
	}

	public WebElement findWebElement(String strXpath) {
		syncFor(By.xpath(strXpath));
		return getDriver().findElement(By.xpath(strXpath));
	}

	public List<WebElement> findWebElements(By byInfo) {
		new WebDriverWait(getDriver(), GLOBAL_SYNC_WAIT_TIME+10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byInfo));
		return getDriver().findElements(byInfo);
	}

	public String getText(By byInfo) {
		syncFor(byInfo);
		return findWebElement(byInfo).getText();
	}

	public String getText(WebElement webElement) {
		String strTextFound="";
		try {
			syncFor(webElement,5);
			strTextFound=webElement.getText();
			consoleOutput("TEXT FOUND: "+strTextFound);
		}catch(Exception e) {}
		return strTextFound;
	}

	public boolean click(By byInfo, String strInfo) {
		try {
			findWebElement(byInfo).click();
			consoleOutput("CLICKED ON "+strInfo.toUpperCase());
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean click(String strXPath, String strInfo) {
		try {
			syncFor(By.xpath(strXPath));
			findWebElement(By.xpath(strXPath)).click();
			consoleOutput("CLICKED ON "+strInfo.toUpperCase());
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean clear(WebElement webElement, String strInfo) {
		try {
			syncFor(webElement);
			webElement.clear();
			consoleOutput("CLEARED "+strInfo.toUpperCase());
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean type(WebElement webElement,String strTextToEnter, String strInfo) {
		try {
			syncFor(webElement);
			clear(webElement,strInfo.toUpperCase());
			webElement.sendKeys(strTextToEnter);
			consoleOutput("ENTERED "+strTextToEnter+" ON "+strInfo.toUpperCase());
			return true;
		}catch(Exception e) {
			//e.printStackTrace();
		}
		return false;
	}

	public boolean typeAndSelect(WebElement webElement,String strTextToEnter, String strInfo) {
		try {
			syncFor(webElement);
			clear(webElement,strInfo);
			click(webElement,strInfo);
			webElement.sendKeys(strTextToEnter);
			findWebElements(By.xpath("//ul[contains(@style,'display: block')]/li/a")).get(0).click();
			consoleOutput("ENTERED AND SELECTED "+strTextToEnter+" ON "+strInfo.toUpperCase().toUpperCase());
			return true;
		}catch(Exception e) {
			//e.printStackTrace();
		}
		return false;
	}

	public boolean click(WebElement webElement, String strInfo) {
		try {
			syncFor(webElement,5);
			webElement.click();
			consoleOutput("CLICKED ON "+strInfo.toUpperCase());
			waitFor(100);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean launch(String strURL) {
		try {
			getDriver().get(strURL);
			consoleOutput("LAUNCHED URL: "+strURL);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void switchToFrame(String strID) {
		switchToDefaultContent();
		waitFor(2000);// Added for slow internet -> For testing purpose -> Need to remove
		syncFor(By.id(strID),10);
		if(isWebElementPresent(By.id(strID))) {
			getDriver().switchTo().frame(strID);
			consoleOutput("SWITCHED TO FRAME: "+strID);
			pageLoadWait();
		}
		else
			consoleOutput("UNABLE TO SWITCH TO THE GIVEN FRAME CONTROLLER");
	}
	
	public void switchToDefaultContent() {
		getDriver().switchTo().defaultContent();
	}

	public boolean isWebElementPresent(String strXpath) {
		try {
			new WebDriverWait(getDriver(),GLOBAL_SYNC_WAIT_TIME).until(ExpectedConditions.presenceOfElementLocated(By.xpath(strXpath)));
			if(findWebElement(By.xpath(strXpath)).isEnabled())
				return true;
		}catch(Exception e) {}
		return false;
	}

	public boolean isWebElementPresent(By byInfo) {
		try {
			new WebDriverWait(getDriver(),GLOBAL_SYNC_WAIT_TIME+5).until(ExpectedConditions.presenceOfElementLocated(byInfo));
			if(findWebElement(byInfo).isEnabled())
				return true;
		}catch(Exception e) {}
		return false;
	}

	public boolean isWebElementPresent(String strXpath, int iTimeOut) {
		try {
			new WebDriverWait(getDriver(),iTimeOut).until(ExpectedConditions.presenceOfElementLocated(By.xpath(strXpath)));
			if(findWebElement(By.xpath(strXpath)).isEnabled())
				return true;
		}catch(Exception e) {}
		return false;
	}

	public boolean isWebElementPresent(WebElement webElement) {
		try {
			new WebDriverWait(getDriver(),GLOBAL_SYNC_WAIT_TIME).until(ExpectedConditions.visibilityOf(webElement));
			if(webElement.isEnabled())
				return true;
		}catch(Exception e) {}
		return false;
	}

	public boolean isWebElementPresent(WebElement webElement, int iTimeOut) {
		try {
			new WebDriverWait(getDriver(),iTimeOut).until(ExpectedConditions.visibilityOf(webElement));
			if(webElement.isEnabled())
				return true;
		}catch(Exception e) {}
		return false;
	}

	public void selectByVisibleText(WebElement webElement,String strTextToSelect,String strInfo) {
		syncFor(webElement,5);
		new Select(webElement).selectByVisibleText(strTextToSelect);
		consoleOutput("SELECTED "+strTextToSelect+" FROM "+ strInfo.toUpperCase());
	}

	public void selectDate(WebElement webElement,String strDateToSelect,String strInfo) {

		click(webElement,strInfo);

		//Look whether its an integer and non empty
		if((strDateToSelect.matches("-?\\d+") || !strDateToSelect.equals(""))) {
			//If it is present it will select the date
			if(isWebElementPresent("//a[contains(@class,'ui-state-default') and not(@class='ui-state-default') and text()='"+strDateToSelect+"']",1)) {
				click("//a[contains(@class,'ui-state-default') and not(@class='ui-state-default') and text()='"+strDateToSelect+"']",strDateToSelect);
				consoleOutput("SELECTED "+strDateToSelect+" FROM "+ strInfo.toUpperCase());
				return;
			}
		}

		//If given date is not available, it will select any available date
		strDateToSelect="Any Available Date";
		click(findWebElements(By.xpath("//a[contains(@class,'ui-state-default') and not(@class='ui-state-default')]")).get(0),"Available Date");

		consoleOutput("SELECTED "+strDateToSelect+" FROM "+ strInfo.toUpperCase());
	}

	public void throwError(String strError) {
		throw new bookingException(strError);
	}

	public void consoleOutput(String strInfo) {
		System.out.println("INFO: "+strInfo);
	}

	public void syncFor(WebElement webElement) {
		try {
			new WebDriverWait(getDriver(), GLOBAL_SYNC_WAIT_TIME).until(ExpectedConditions.elementToBeClickable(webElement));
		}catch(Exception e) {}
	}

	public void syncFor(By byInfo) {
		try {
			new WebDriverWait(getDriver(), GLOBAL_SYNC_WAIT_TIME).until(ExpectedConditions.elementToBeClickable(byInfo));
		}catch(Exception e) {}
	}

	public void syncFor(By byInfo,int iAdditionalTimeoutInSeconds) {
		try {
			new WebDriverWait(getDriver(), GLOBAL_SYNC_WAIT_TIME+iAdditionalTimeoutInSeconds).until(ExpectedConditions.elementToBeClickable(byInfo));
		}catch(Exception e) {}
	}

	public void syncFor(WebElement webElement,int iAdditionalTimeoutInSeconds) {
		try {
			new WebDriverWait(getDriver(), GLOBAL_SYNC_WAIT_TIME+iAdditionalTimeoutInSeconds).until(ExpectedConditions.elementToBeClickable(webElement));
		}catch(Exception e) {}
	}
	
	public void pageLoadWait() {
		try{
			new WebDriverWait(getDriver(), GLOBAL_SYNC_WAIT_TIME).until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
		}catch(Exception e) {}
	}
}
