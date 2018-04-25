package deckers;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
import saucelabs.CapabilitiesDataProvider;
import saucelabs.SauceTestBase;
import saucelabs.TestSettings;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePageTest extends SauceTestBase
{
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getSingleTestSettings")
	public void goToBrandsPage(TestSettings settings)
	{
		RemoteWebDriver browser = getBrowser(settings);

		HomePage page = deckers.HomePage.Open(browser);

		assertThat(page.title()).isEqualTo(HomePage.Expected.title);

		page.clickBrands();

//		asserThat(page.title()).isEqualTo(BrandsPage.Expected.title);
	}
}
