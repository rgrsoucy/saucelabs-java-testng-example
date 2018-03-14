import java.lang.reflect.Method;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CapabilitiesDataProviderTest
{
	// expects SAUCE_ONDEMAND_BROWSERS environment variable to be set
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getMultipleTestSettings")
	public void testMultipleCapabilities(TestSettings testSettings, Method method)
	{
		System.out.println(testSettings);

		assertThat(testSettings).isNotNull();
		assertThat(testSettings.platform).isNotEmpty();
		assertThat(testSettings.browserName).isNotEmpty();
		assertThat(testSettings.browserVersion).isNotEmpty();
	}

	// expects SELENIUM_PLATFORM, SELENIUM_BROWSER, SELENIUM_VERSION environment variables to be set
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getSingleTestSettings")
	public void testSingleCapabilities(TestSettings testSettings, Method method)
	{
		System.out.println(testSettings);

		assertThat(testSettings).isNotNull();
		assertThat(testSettings.platform).isNotEmpty();
		assertThat(testSettings.browserName).isNotEmpty();
		assertThat(testSettings.browserVersion).isNotEmpty();
	}
}