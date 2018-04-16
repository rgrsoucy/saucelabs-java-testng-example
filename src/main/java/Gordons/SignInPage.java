package Gordons;

import org.openqa.selenium.By;

public class SignInPage
{
	public static String url = "https://apps.gfs.com/experience/";

	public static class Expected
	{
		public static String title = "Customer Sign In";
	}

	public static By usernameField = By.id("j_username");
	public static By passwordField = By.id("j_password");
	public static By signInButton = By.id("submit");

	public static By error = By.className("error");
}
