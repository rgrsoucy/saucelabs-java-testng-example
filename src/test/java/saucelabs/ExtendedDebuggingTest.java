package saucelabs;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtendedDebuggingTest extends SauceTestBase
{
	@Test
	public void simpleTest(Method method)
	{
		DesiredCapabilities capabilities = getDesiredCapabilities("Windows 10", "Chrome", "latest");
		capabilities.setCapability("extendedDebugging", true);

		RemoteWebDriver browser = getBrowser(capabilities);
		browser.get("https://saucelabs.com");
		String title = browser.getTitle();

		System.out.println(title);
		assertThat(title).contains("Sauce Labs");
	}
}