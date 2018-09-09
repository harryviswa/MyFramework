package com.apps.base;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.sun.javafx.PlatformUtil;

@SuppressWarnings("restriction")
public abstract class CommonTestBase {

	public static ThreadLocal<RemoteWebDriver> localDriver=new ThreadLocal<RemoteWebDriver>();

	@BeforeMethod
	@Parameters({"browserToUse"})
	public void initDrivers(@Optional("chrome") String strBrowserToUse) {
		setDriverPath();
		System.out.println("BROWSER TYPE: "+strBrowserToUse);
		switch (strBrowserToUse.toLowerCase())
		{
		case "firefox":
			try {
				localDriver.set(new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), DesiredCapabilities.firefox()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "chrome":
		default:
			//ChromeDriver driverToUse=new ChromeDriver();
			try {
				localDriver.set(new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), DesiredCapabilities.chrome()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		getDriver().manage().deleteAllCookies();
		//getDriver().manage().window().maximize();
	}

	public RemoteWebDriver getDriver() {
		return localDriver.get();
	}

	public void setDriver(RemoteWebDriver driverToUse) {
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
