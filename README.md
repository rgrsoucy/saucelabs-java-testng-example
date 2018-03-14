# saucelabs-java-testng-example

This project shows how to use Sauce Labs with TestNG using the SauceOnDemand Jenkins plugin:

For a single browser, it sets the following environment variables:

  SELENIUM_PLATFORM
  SELENIUM_BROWSER
  SELENIUM_VERSION
  
For multiple browsers, it sets the environment variable:

  SAUCE_ONDEMAND_BROWSERS
  
This contains a JSON array with information about each browser:

The sample tests use a DataProvider class to get the WebDriver capabilities set by SaucenDemand Jenkins plugin.
Tests then run in parallel across all browsers.
