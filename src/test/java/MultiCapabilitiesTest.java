import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiCapabilitiesTest extends SauceTestBase
{
	// expects SAUCE_ONDEMAND_BROWSERS environment variable to be set
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getMultipleTestSettings")
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
