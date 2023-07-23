package com.apps.base;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.apps.datadrivers.TestContext;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.qameta.allure.Attachment;

public abstract class CommonTestBase {

	public static ThreadLocal<WebDriver> localDriver=new ThreadLocal<WebDriver>();
	public static ExtentReports reports = new ExtentReports();
	protected static ThreadLocal<ExtentTest> tests=new ThreadLocal<ExtentTest>();
	ExtentSparkReporter spark = new ExtentSparkReporter("SparkExtent.html");
	public static final Logger log = LogManager.getLogger(CommonTestBase.class);
	public static boolean seleniumGrid=false;
	protected TestContext tcm;
	
	static {
		PropertyConfigurator.configure(System.getProperty("user.dir") +"/src/main/resources/log4j.properties");
		BasicConfigurator.configure();
		setDriverPath();
	}
	
	@BeforeMethod
	@Parameters({"browserToUse"})
	public void initDrivers(ITestContext context, @Optional("firefox") String strBrowserToUse) throws MalformedURLException {
		tcm=new TestContext(); 
		if(context.getCurrentXmlTest().getAllParameters().containsKey("grid"))
		seleniumGrid=Boolean.parseBoolean(context.getCurrentXmlTest().getAllParameters().get("grid").toString());
			
		switch(strBrowserToUse) {
		case "chrome":
			ChromeOptions options =new ChromeOptions();
			options.addArguments("--remote-allow-origins=*","ignore-certificate-errors","--disable-extensions");
			options.setCapability(CapabilityType.PLATFORM_NAME, Platform.MAC);
			options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, PageLoadStrategy.NORMAL);
			setDriver(new ChromeDriver(options));
			break;
		case "firefox":
		default:
			FirefoxOptions fxoptions =new FirefoxOptions();
			fxoptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.MAC);
			fxoptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			fxoptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, PageLoadStrategy.NORMAL);
			//fxoptions.addArguments("-headless");
			setDriver(seleniumGrid?(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), fxoptions)):(new FirefoxDriver(fxoptions)));
		
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
	}
	
	@BeforeClass
	public void entryConfig(ITestContext context) {
		reports.attachReporter(spark);
	}

	public WebDriver getDriver() {
		return localDriver.get();
	}

	public void resetZoomLevel() {
		JavascriptExecutor js=(JavascriptExecutor)getDriver(); 
		js.executeScript("document.body.style.zoom='100%'");
	}
	
	public void setDriver(RemoteWebDriver driverToUse) {
		localDriver.set(driverToUse);
	}

	private static void setDriverPath() {
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/main/resources/libs/chromedriver");
		reports.setUsingNaturalConf(true);	
	}
	
	public void throwError(String strError) {
		throw new customException(strError);
	}

	public void consoleOutput(String strInfo) {
		if(tests.get()!=null)
		tests.get().log(Status.INFO, strInfo);
		log.info(strInfo);
	}
	
	public void assertStep(boolean bSuccess,String validationInfo) {
		captureScreenshot();
		tests.get().log(bSuccess?Status.PASS:Status.FAIL, validationInfo);
		assertEquals(bSuccess, true);
	}

	@AfterMethod
	public void tearDown() {
		reports.flush();
		getDriver().quit();
	}
	
	public void captureScreenshot() {
		addToReport(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES));
	}
	
	@Attachment(value="Capture screenshot",type="image/png")
	public byte[] addToReport(byte[] image) {
		Assert.assertTrue("Screenshot", true);
		tests.get().log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(image)).build());
		return image;
	}

}

class customException extends TimeoutException{
	private static final long serialVersionUID = 1L;

	public customException(String strErrorMessage) {
		super(strErrorMessage);
		CommonTestBase.tests.get().log(Status.FAIL, strErrorMessage);
	}
}
