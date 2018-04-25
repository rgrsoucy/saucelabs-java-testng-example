package saucelabs;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.Test;
import saucelabs.FileLoader;
import saucelabs.TestSettings;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSettingsTest
{
	@Test
	public void parseMultiCapabilitiesTest() throws IOException
	{
		String SAUCE_ONDEMAND_BROWSERS = System.getenv("SAUCE_ONDEMAND_BROWSERS");

		/* normally get from environment variable but load from file for testing */
		if (SAUCE_ONDEMAND_BROWSERS == null)
		{
			SAUCE_ONDEMAND_BROWSERS = FileLoader.loadResourceFile("SAUCE_ONDEMAND_BROWSERS.json");
		}

		List<TestSettings> testSettingsList = TestSettings.parseMultiCapabilities(SAUCE_ONDEMAND_BROWSERS);

		System.out.println(testSettingsList);

		assertThat(testSettingsList.size()).isEqualTo(3);

		for(TestSettings testSettings : testSettingsList)
		{
			assertThat(testSettings.platform).isNotEmpty();
			assertThat(testSettings.browserName).isNotEmpty();
			assertThat(testSettings.browserVersion).isNotEmpty();
		}
	}
}
