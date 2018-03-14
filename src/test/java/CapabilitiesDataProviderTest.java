import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class CapabilitiesDataProviderTest
{
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getMultipleTestSettings")
	public void testMultipleCapabilities(TestSettings testSettings, Method method)
	{
		System.out.println(testSettings);

		assertThat(testSettings).isNotNull();
		assertThat(testSettings.platform).isNotEmpty();
		assertThat(testSettings.browserName).isNotEmpty();
		assertThat(testSettings.browserVersion).isNotEmpty();
	}

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