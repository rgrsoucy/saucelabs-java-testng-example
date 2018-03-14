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
		String sessionId = browser.get().getSessionId().toString();

		if (result.isSuccess())
		{
			api.jobPassed(sessionId);
		}
		else
		{
			api.jobFailed(sessionId);
		}

		browser.get().quit();
	}


	public String getTestName(Method method)
	{
		return this.getClass().getSimpleName() + " " + method.getName();
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

	public static DesiredCapabilities getDesiredCapabilities(String SELENIUM_PLATFORM, String SELENIUM_BROWSER, String SELENIUM_VERSION, String TEST_NAME, String BUILD_TAG)
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (SELENIUM_PLATFORM != null)
		{
			capabilities.setCapability("platform", SELENIUM_PLATFORM);
		}

		if (SELENIUM_BROWSER != null)
		{
			capabilities.setCapability("browserName", SELENIUM_BROWSER);
		}

		if (SELENIUM_VERSION != null)
		{
			capabilities.setCapability("version", SELENIUM_VERSION);
		}

		if (TEST_NAME != null)
		{
			capabilities.setCapability("name", TEST_NAME + " on " + SELENIUM_PLATFORM + " " + SELENIUM_BROWSER + " " + SELENIUM_VERSION);
		}

		if (BUILD_TAG != null)
		{
			capabilities.setCapability("build", BUILD_TAG);
		}

		return capabilities;
	}

	public static SauceREST getSauceRestApi(String SAUCE_USERNAME, String SAUCE_ACCESS_KEY)
	{
		return new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);
	}

	public RemoteWebDriver getBrowser(TestSettings settings)
	{
		DesiredCapabilities capabilities = getDesiredCapabilities(settings.platform, settings.browserName, settings.browserVersion, TEST_NAME, BUILD_TAG);
		browser.set(new RemoteWebDriver(webdriverURL, capabilities));

		return browser.get();
	}
}