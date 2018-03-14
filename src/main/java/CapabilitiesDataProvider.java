import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CapabilitiesDataProvider
{
	@DataProvider(name = "getMultipleBrowserSettings", parallel = true)
	public Iterator<Object> getMultipleBrowserSettings() throws IOException
	{
		String SAUCE_ONDEMAND_BROWSERS = System.getenv("SAUCE_ONDEMAND_BROWSERS");

		/* normally get from environment variable but load from file for testing */
		SAUCE_ONDEMAND_BROWSERS = FileLoader.loadResourceFile("SAUCE_ONDEMAND_BROWSERS.json");

		System.out.println("SAUCE_ONDEMAND_BROWSERS:" + SAUCE_ONDEMAND_BROWSERS);

		List<TestSettings> testSettingsList;

		if (SAUCE_ONDEMAND_BROWSERS == null)
		{
			return getSingleBrowserSettings();
		}

		testSettingsList = TestSettings.parseMultiCapabilities(SAUCE_ONDEMAND_BROWSERS);

		return convertToTestNGDataProvider(testSettingsList);
	}

	@DataProvider(name = "getSingleBrowserSettings", parallel = true)
	public Iterator<Object> getSingleBrowserSettings()
	{
		String SELENIUM_PLATFORM = System.getenv("SELENIUM_PLATFORM");
		String SELENIUM_BROWSER = System.getenv("SELENIUM_BROWSER");
		String SELENIUM_VERSION = System.getenv("SELENIUM_VERSION");

		/* array with only one test settings */
		List<TestSettings> testSettingsList = new ArrayList<TestSettings>()
		{{
			new TestSettings()
			{{
				platformName = SELENIUM_PLATFORM;
				browserName = SELENIUM_BROWSER;
				browserVersion = SELENIUM_VERSION;
			}};
		}};

		return convertToTestNGDataProvider(testSettingsList);
	}

	public Iterator<Object> convertToTestNGDataProvider(List<TestSettings> testSettingsList)
	{
		/* convert to Iterator<Object> to make TestNG happy */
		 return new ArrayList<Object>(testSettingsList).iterator();
	}
}