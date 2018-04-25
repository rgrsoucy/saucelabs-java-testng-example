package gordons;

import gordons.SignInPage;
import com.saucelabs.saucerest.SauceREST;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
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
	public void uploadFile()
	{

	}

	@Test
	public void with_invalid_credentials(Method method) throws MalformedURLException
	{
		URL url = new URL(SAUCE_URL);


		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability("browserName", "Internet Explorer");
		capabilities.setCapability("version", "11");
		capabilities.setCapability("extendedDebugging", true);
		capabilities.setCapability("name", this.getClass().getSimpleName() + " " + method.getName());

		RemoteWebDriver driver = new RemoteWebDriver(url, capabilities);
		String sessionId = driver.getSessionId().toString();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		SauceREST api = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);

		// open home page
		driver.executeScript("sauce:context=Open Home Page");
		driver.get(gordons.HomePage.url);
		assertThat(driver.getTitle()).isEqualTo(gordons.HomePage.Expected.title);

		// Go to sign in page
		driver.executeScript("sauce:context=Go to Sign In Page");
		driver.findElement(gordons.HomePage.signInButton).click();

		// should be on sign in page
		wait.until(ExpectedConditions.urlToBe(gordons.SignInPage.url));
		assertThat(driver.getTitle()).isEqualTo(gordons.SignInPage.Expected.title);


		// sign in with invalid credentials
		driver.executeScript("sauce:context=Sign in with invalid credentials");

		driver.findElement(gordons.SignInPage.usernameField).sendKeys(invalidUsername);
		driver.findElement(gordons.SignInPage.passwordField).sendKeys(invalidPassword);
		driver.findElement(gordons.SignInPage.signInButton).click();

		// check the error message
		String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(gordons.SignInPage.error)).getText();
		driver.executeScript("sauce:context=Error Message: "+ errorMessage);

		try
		{
			assertThat(errorMessage).isEqualTo(SignInPage.Expected.errorMessage);
			api.jobPassed(sessionId);
		}
		catch (AssertionError e)
		{
			api.jobFailed(sessionId);
		}


		driver.quit();
	}
}
