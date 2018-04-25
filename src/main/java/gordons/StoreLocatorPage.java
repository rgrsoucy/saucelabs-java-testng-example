package gordons;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import saucelabs.PageObject;

import java.util.List;

public class StoreLocatorPage extends PageObject
{
	public static String url = "https://gfsstore.com/locations/";

	public static class Expected
	{
		public static String title = "Store Locations Archive - Gordon Food Service Store";
	}

	public static By addressField = By.id("address");
	public static By goButton = By.cssSelector("[name=address-submit]");

	public static By locationCount = By.className("location-results-count");
	public static By locations = By.className("location");

	public static By locationMoreDetail = By.cssSelector(".locator-location-more-detail");
	public static By locationDistance = By.cssSelector(".location-distance");


	public static StoreLocatorPage Open(WebDriver driver)
	{
		return new StoreLocatorPage(driver).open();
	}

	public StoreLocatorPage(WebDriver driver)
	{
		super(driver);
	}

	public StoreLocatorPage open()
	{
		driver.get(url);
		return this;
	}

	public StoreLocatorPage findByAddress(String address)
	{
		type(addressField, address);
		click(goButton);

		return this;
	}

	public List<WebElement> getLocations()
	{
		return driver.findElements(locations);
	}

	public String getLocationName(WebElement location)
	{
		return location.findElement(locationMoreDetail).getText();
	}

	public String getLocationDistance(WebElement location)
	{
		return location.findElement(locationDistance).getText();
	}

	public String getNumberOfLocations()
	{
		return find(locationCount).getText();
	}

}
