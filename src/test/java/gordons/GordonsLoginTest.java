package gordons;

import com.saucelabs.saucerest.SauceREST;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class GordonsLoginTest
{
	String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	String SAUCE_URL = "https://SAUCE_USERNAME:SAUCE_ACCESS_KEY@ondemand.saucelabs.com:443/wd/hub"
			.replace("SAUCE_USERNAME", SAUCE_USERNAME)
			.replace("SAUCE_ACCESS_KEY", SAUCE_ACCESS_KEY);


	@Test
	public void shouldOpenHomePage() throws Exception
	{
		SauceREST api = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);

		URL url = new URL(SAUCE_URL);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "Internet Explorer");
		capabilities.setCapability("version", "11");
		capabilities.setCapability("name", "HomePage Test");

		RemoteWebDriver driver = new RemoteWebDriver(url, capabilities);
		String sessionId = driver.getSessionId().toString();

		driver.get("https://www.gfs.com");
		String title = driver.getTitle();
		driver.executeScript("sauce:context=got title: " + title);
		try
		{
			assertThat(title).isEqualTo(HomePage.Expected.title);
			driver.executeScript("sauce:job-result=passed");
		} catch (AssertionError e)
		{
			driver.executeScript("sauce:job-result=fail");
		}

		driver.quit();



	}
}
