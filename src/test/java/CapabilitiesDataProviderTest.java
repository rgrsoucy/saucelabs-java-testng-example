import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class CapabilitiesDataProviderTest
{
	@Test(dataProviderClass = CapabilitiesDataProvider.class, dataProvider = "getMultipleTestSettings")
	public void test(TestSettings settings, Method method)
	{
		System.out.println(settings);
	}
}
