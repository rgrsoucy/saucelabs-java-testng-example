import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.Test;

import java.net.URL;

public class TestMultiCapabilities extends SauceTestBase
{
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getMultipleTestSettings")
	public void simpleTest(TestSettings settings)
	{
		System.out.println("got settings: " + settings);

		RemoteWebDriver browser = getBrowser(settings);
		browser.get("https://saucelabs.com");

		System.out.println(browser.getTitle());
	}
}
