package saucelabs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.util.List;

public class TestSettings
{
//	@SerializedName("os")
//	public String os;

	@SerializedName("platform")
	public String platform;

	@SerializedName("browser")
	public String browserName;

	@SerializedName("browser-version")
	public String browserVersion;

//	@SerializedName("long-name")
//	public String browserNameLong;
//
//	@SerializedName("long-version")
//	public String browserVersionLong;
//
//	@SerializedName("url")
//	public String url;

	public static Gson parser = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

	public static List<TestSettings> parseMultiCapabilities(String json)
	{
		Type TestSettingsListType = new TypeToken<List<TestSettings>>(){}.getType();

		return parser.fromJson(json, TestSettingsListType);
	}

	public TestSettings fromJson(String json)
	{
		Type TestSettingsType = new TypeToken<TestSettings>(){}.getType();
		return parser.fromJson(json, TestSettingsType);
	}

	public String toJson()
	{
		return parser.toJson(this);
	}

	public String toString()
	{
		return toJson();
	}
}