import com.saucelabs.saucerest.SauceREST;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class SignInTest
{
	String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	String SAUCE_URL = "https://SAUCE_USERNAME:SAUCE_ACCESS_KEY@ondemand.saucelabs.com:443/wd/hub"
			.replace("SAUCE_USERNAME", SAUCE_USERNAME)
			.replace("SAUCE_ACCESS_KEY", SAUCE_ACCESS_KEY);

	String invalidUsername = "aaron";
	String invalidPassword = "secret";

	@Test
	public void with_invalid_credentials() throws MalformedURLException
	{
		URL url = new URL(SAUCE_URL);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability("browserName", "Internet Explorer");
		capabilities.setCapability("version", "11");

		RemoteWebDriver driver = new RemoteWebDriver(url, capabilities);
		String sessionId = driver.getSessionId().toString();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		SauceREST api = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);

		// open home page
		driver.executeScript("sauce:context=Open Home Page");
		driver.get(Gordons.HomePage.url);
		assertThat(driver.getTitle()).isEqualTo(Gordons.HomePage.Expected.title);

		// Go to sign in page
		driver.executeScript("sauce:context=Go to Sign In Page");
		driver.findElement(Gordons.HomePage.signInButton).click();

		// should be on sign in page
		wait.until(ExpectedConditions.urlToBe(Gordons.SignInPage.url));
		assertThat(driver.getTitle()).isEqualTo(Gordons.SignInPage.Expected.title);


		// sign in with invalid credentials
		driver.executeScript("sauce:context=Sign in with invalid credentials");

		driver.findElement(Gordons.SignInPage.usernameField).sendKeys(invalidUsername);
		driver.findElement(Gordons.SignInPage.passwordField).sendKeys(invalidPassword);
		driver.findElement(Gordons.SignInPage.signInButton).click();

		// check the error message
		String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(Gordons.SignInPage.error)).getText();
		driver.executeScript("sauce:context=Error Message: "+ errorMessage);

		try
		{
			assertThat(errorMessage).isEqualTo("Invalid username or password");
			api.jobPassed(sessionId);
		}
		catch (AssertionError e)
		{
			api.jobFailed(sessionId);
		}


		driver.quit();
	}
}
