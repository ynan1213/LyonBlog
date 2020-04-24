import java.net.URL;

import org.springframework.core.io.support.SpringFactoriesLoader;

public class ResourceTest
{
	public static void main(String[] args)
	{
		URL url = SpringFactoriesLoader.class.getClassLoader().getResource("META-INF/spring.factories");
		String path = url.getPath();
		System.out.println(path);
	}
}
