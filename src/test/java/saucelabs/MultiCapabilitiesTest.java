package saucelabs;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.SkipException;
import org.testng.annotations.Test;
import saucelabs.CapabilitiesDataProvider;
import saucelabs.TestSettings;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiCapabilitiesTest extends saucelabs.SauceTestBase
{
	// expects SAUCE_ONDEMAND_BROWSERS environment variable to be set
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getMultipleTestSettings")
	public void simpleTest(TestSettings settings)
	{
		System.out.println("got settings: " + settings);

		// expects SAUCE_ONDEMAND_BROWSERS environment variable to be set
		if (System.getenv("SAUCE_ONDEMAND_BROWSERS") == null)
		{
			throw new SkipException("Test skipped because SAUCE_ONDEMAND_BROWSERS was not set");
		}

		RemoteWebDriver browser = getBrowser(settings);
		browser.get("https://saucelabs.com");

		String title = browser.getTitle();

		System.out.println(title);

		assertThat(title).contains("Sauce Labs");
	}
}
