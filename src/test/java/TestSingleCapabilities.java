import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.Test;

public class TestSingleCapabilities extends SauceTestBase
{
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getSingleTestSettings")
	public void simpleTest(TestSettings settings)
	{
		System.out.println("got settings: " + settings);

		RemoteWebDriver browser = getBrowser(settings);
		browser.get("https://saucelabs.com");

		System.out.println(browser.getTitle());
	}
}
