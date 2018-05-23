package com.apps.base;

import java.io.File;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.sun.javafx.PlatformUtil;

@SuppressWarnings("restriction")
public abstract class CommonTestBase {

	public static ThreadLocal<WebDriver> localDriver=new ThreadLocal<WebDriver>();

	@BeforeMethod
	@Parameters({"browserToUse"})
	public void initDrivers(@Optional("chrome") String strBrowserToUse) {
		setDriverPath();
		switch (strBrowserToUse.toLowerCase())
		{
		case "firefox":
		case "chrome":
		default:
			ChromeDriver driverToUse=new ChromeDriver();
			localDriver.set(driverToUse);
			break;
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
	}

	public WebDriver getDriver() {
		return localDriver.get();
	}

	public void setDriver(WebDriver driverToUse) {
		localDriver.set(driverToUse);
	}

	private void setDriverPath() {
		if (PlatformUtil.isMac()) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/main/resources/libs/chromedriver");
		}
		if (PlatformUtil.isWindows()) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/main/resources/libs/chromedriver.exe");
		}
		if (PlatformUtil.isLinux()) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/main/resources/libs/chromedriver_linux");
		}
	}

	@AfterMethod(alwaysRun=true)
	public void exitCriteria(ITestResult result) {
		try {
			String strTimeStamp=(new Timestamp(System.currentTimeMillis()).getTime())+"";
			File srcFile=((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			String strDestFile=System.getProperty("user.dir")+"/Screenshot-"+result.getName()+strTimeStamp+".png";//+ Date.valueOf(LocalDate.now());
			FileUtils.copyFile(srcFile, new File(strDestFile));
		}catch(Exception e) {}

		getDriver().quit();
	}

}
