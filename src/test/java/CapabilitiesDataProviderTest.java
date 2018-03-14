import java.lang.reflect.Method;

import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CapabilitiesDataProviderTest
{
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getMultipleTestSettings")
	public void testMultipleCapabilities(TestSettings settings, Method method)
	{
		System.out.println(settings);

		// expects SAUCE_ONDEMAND_BROWSERS environment variable to be set
		if (System.getenv("SAUCE_ONDEMAND_BROWSERS") == null)
		{
			throw new SkipException("Test skipped because SAUCE_ONDEMAND_BROWSERS was not set");
		}

		assertThat(settings).isNotNull();
		assertThat(settings.platform).isNotEmpty();
		assertThat(settings.browserName).isNotEmpty();
		assertThat(settings.browserVersion).isNotEmpty();
	}

	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getSingleTestSettings")
	public void testSingleCapabilities(TestSettings settings, Method method)
	{
		System.out.println(settings);

		// expects SELENIUM_PLATFORM, SELENIUM_BROWSER, SELENIUM_VERSION environment variables to be set
		if (System.getenv("SELENIUM_BROWSER") == null)
		{
			throw new SkipException("Test skipped because SELENIUM_BROWSER was not set");
		}

		assertThat(settings).isNotNull();
		assertThat(settings.platform).isNotEmpty();
		assertThat(settings.browserName).isNotEmpty();
		assertThat(settings.browserVersion).isNotEmpty();
	}
}