import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleCapabilitiesTest extends SauceTestBase
{
	// expects SELENIUM_PLATFORM, SELENIUM_BROWSER, SELENIUM_VERSION environment variables to be set
	@Ignore@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getSingleTestSettings")
	public void simpleTest(TestSettings settings)
	{
		System.out.println("got settings: " + settings);

		RemoteWebDriver browser = getBrowser(settings);
		browser.get("https://saucelabs.com");

		String title = browser.getTitle();

		System.out.println(title);

		assertThat(title).contains("Sauce Labs");
	}
}
