package saucelabs;

import org.testng.annotations.DataProvider;
import saucelabs.TestSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CapabilitiesDataProvider
{
	@DataProvider(name = "getMultipleTestSettings", parallel = true)
	public Iterator<Object> getMultipleTestSettings() throws IOException
	{
		String SAUCE_ONDEMAND_BROWSERS = System.getenv("SAUCE_ONDEMAND_BROWSERS");

		List<TestSettings> testSettingsList;

		if (SAUCE_ONDEMAND_BROWSERS == null)
		{
			return getSingleTestSettings();
		}

		testSettingsList = TestSettings.parseMultiCapabilities(SAUCE_ONDEMAND_BROWSERS);

		return convertToTestNGDataProvider(testSettingsList);
	}

	@DataProvider(name = "getSingleTestSettings", parallel = true)
	public Iterator<Object> getSingleTestSettings()
	{
		String SELENIUM_PLATFORM = System.getenv("SELENIUM_PLATFORM");
		String SELENIUM_BROWSER = System.getenv("SELENIUM_BROWSER");
		String SELENIUM_VERSION = System.getenv("SELENIUM_VERSION");

		TestSettings testSettings = new TestSettings();
		testSettings.platform = SELENIUM_PLATFORM;
		testSettings.browserName = SELENIUM_BROWSER;
		testSettings.browserVersion = SELENIUM_VERSION;

		/* array with only one test settings */
		List<TestSettings> testSettingsList = new ArrayList<>();
		testSettingsList.add(testSettings);

		return convertToTestNGDataProvider(testSettingsList);
	}

	public Iterator<Object> convertToTestNGDataProvider(List<TestSettings> testSettingsList)
	{
		/* convert to Iterator<Object> to make TestNG happy */
		 return new ArrayList<Object>(testSettingsList).iterator();
	}
}