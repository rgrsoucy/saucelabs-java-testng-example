import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader
{
	public static String loadResourceFile(String fileName) throws IOException
	{
		String path = ClassLoader.getSystemResource(fileName).getFile();
		return new String(Files.readAllBytes(Paths.get(path)), Charset.defaultCharset());
	}
}