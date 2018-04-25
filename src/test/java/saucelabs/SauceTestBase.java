package saucelabs;

import com.saucelabs.saucerest.SauceREST;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class SauceTestBase
{
	String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	String BUILD_TAG = System.getenv("BUILD_TAG");
	String TEST_NAME;

	URL webdriverURL;
	SauceREST api;

	protected ThreadLocal<RemoteWebDriver> browser = new ThreadLocal<>();

	@BeforeMethod
	public void setup(Method method)
	{
		TEST_NAME = getTestName(method);
		webdriverURL = getSauceUrl(SAUCE_USERNAME, SAUCE_ACCESS_KEY);
		api = getSauceRestApi(SAUCE_USERNAME, SAUCE_ACCESS_KEY);
	}

	@AfterMethod
	public void teardown(ITestResult result)
	{
		RemoteWebDriver browser = this.browser.get();

		/* something happened before we got here so we don't need to clean up */
		if (browser == null)
		{
			return;
		}

		String sessionId = browser.getSessionId().toString();

		if (result.isSuccess())
		{
			api.jobPassed(sessionId);
		}
		else
		{
			api.jobFailed(sessionId);
		}

		browser.quit();
		this.browser.remove();
	}


	public String getTestName(Method method)
	{
		return this.getClass().getSimpleName() + " " + method.getName();
	}

	public RemoteWebDriver getBrowser(TestSettings settings)
	{
		DesiredCapabilities capabilities = getDesiredCapabilities(settings.platform, settings.browserName, settings.browserVersion, TEST_NAME, BUILD_TAG);
		browser.set(new RemoteWebDriver(webdriverURL, capabilities));

		return browser.get();
	}

	public static URL getSauceUrl(String SAUCE_USERNAME, String SAUCE_ACCESS_KEY)
	{
		if (SAUCE_USERNAME == null)
		{
			throw new RuntimeException("SAUCE_USERNAME must be set");
		}
		if (SAUCE_ACCESS_KEY == null)
		{
			throw new RuntimeException("SAUCE_ACCESS_KEY must be set");
		}

		String SAUCE_URL = "https://SAUCE_USERNAME:SAUCE_ACCESS_KEY@ondemand.saucelabs.com:443/wd/hub"
				.replace("SAUCE_USERNAME", SAUCE_USERNAME)
				.replace("SAUCE_ACCESS_KEY", SAUCE_ACCESS_KEY);

		try
		{
			return new URL(SAUCE_URL);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static DesiredCapabilities getDesiredCapabilities(String platform, String browserName, String browserVersion, String testName, String buildTag)
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (platform != null)
		{
			capabilities.setCapability("platform", platform);
		}

		if (browserName != null)
		{
			capabilities.setCapability("browserName", browserName);
		}

		if (browserVersion != null)
		{
			capabilities.setCapability("version", browserVersion);
		}

		if (testName != null)
		{
			capabilities.setCapability("name", testName + " on " + platform + " " + browserName + " " + browserVersion);
		}

		if (buildTag != null)
		{
			capabilities.setCapability("build", buildTag);
		}

		capabilities.setCapability("tags", "smoketest,qaenvironment,releaseX,ondate");
		return capabilities;
	}

	public static SauceREST getSauceRestApi(String SAUCE_USERNAME, String SAUCE_ACCESS_KEY)
	{
		return new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);
	}

	public void addContext(String text)
	{
		browser.get().executeScript("sauce:context=" + text);
	}
}