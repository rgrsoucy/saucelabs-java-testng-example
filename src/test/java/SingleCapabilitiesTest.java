import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.SkipException;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleCapabilitiesTest extends SauceTestBase
{
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getSingleTestSettings")
	public void simpleTest(TestSettings settings)
	{
		// expects SELENIUM_PLATFORM, SELENIUM_BROWSER, SELENIUM_VERSION environment variables to be set
		if (System.getenv("SELENIUM_BROWSER") == null)
		{
			throw new SkipException("Test skipped because SELENIUM_BROWSER was not set");
		}

		System.out.println("got settings: " + settings);

		RemoteWebDriver browser = getBrowser(settings);
		browser.get("https://saucelabs.com");

		String title = browser.getTitle();

		System.out.println(title);

		assertThat(title).contains("Sauce Labs");
	}
}
