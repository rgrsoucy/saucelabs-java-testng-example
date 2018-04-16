package Gordons;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject
{
	public WebDriver driver;
	public WebDriverWait wait;

	public long TIMEOUT = 30;

	public PageObject(WebDriver driver)
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, TIMEOUT);
	}


	public WebElement find(By locator)
	{
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void click(By locator)
	{
		find(locator).click();
	}

	public void type(By locator, String text)
	{
		find(locator).sendKeys(text);
	}
}
