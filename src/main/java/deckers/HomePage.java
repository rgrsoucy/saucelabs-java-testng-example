package deckers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import saucelabs.PageObject;

public class HomePage extends PageObject
{
	public HomePage(WebDriver driver)
	{
		super(driver);
	}

	public static String url = "https://www.deckers.com/";

	public static class Expected {
		public static String title = "Fashion Lifestyle and Performance Lifestyle Footwear | Deckers Brands";
		public static String heading = "MOTIVATED BY THE PURSUIT";
	}

	public By heading = By.tagName("h1");
	public By menu = By.cssSelector("[role=navigation");

	public static HomePage Open(WebDriver driver)
	{
		return new HomePage(driver).open();
	}

	public HomePage open()
	{
		driver.get(url);
		return this;
	}

	public void clickBrands()
	{
		find(menu).findElement(By.partialLinkText("Brands")).click();
	}
}
