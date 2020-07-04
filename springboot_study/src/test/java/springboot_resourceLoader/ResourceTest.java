package springboot_resourceLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.springframework.core.io.support.SpringFactoriesLoader;

public class ResourceTest
{
	public static void main(String[] args) throws IOException
	{
		Enumeration<URL> resources = SpringFactoriesLoader.class.getClassLoader().getResources("META-INF/LICENSE");
		while (resources.hasMoreElements())
		{
			System.out.println(resources.nextElement().getPath());
		}
	}
}
