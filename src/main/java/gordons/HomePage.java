package gordons;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage
{
	public static String url = "https://www.gfs.com/";

	public static By signInButton = By.className("sign-in-button");



	public static class Expected
	{
		public static String title = "Gordon Food Service Distribution and Stores";
	}


	public RemoteWebDriver driver;
	public WebDriverWait wait;

	public HomePage(RemoteWebDriver driver)
	{
		this.driver = driver;
	}


	public static HomePage Open(RemoteWebDriver driver)
	{
		return new HomePage(driver);
	}
}
