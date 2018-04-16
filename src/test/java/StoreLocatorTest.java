import Gordons.StoreLocatorPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreLocatorTest extends SauceTestBase
{
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getSingleTestSettings")
	public void findNearestStore(TestSettings settings)
	{
		RemoteWebDriver browser = getBrowser(settings);

		StoreLocatorPage storeLocator = StoreLocatorPage.Open(browser);

		String city = "Grand Rapids";
		addContext("Find stores in " + city);
		storeLocator.findByAddress(city);

		String locationCount = storeLocator.getNumberOfLocations();
		addContext(locationCount);
		assertThat(locationCount).contains("6");

		List<WebElement> locations = storeLocator.getLocations();

		locations.forEach(location ->
			addContext(storeLocator.getLocationName(location)
					+ " "
					+ storeLocator.getLocationDistance(location)
			)
		);

	}
}
