package fatherAndSon;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Son extends Father
{
	public void print()
	{
		System.out.println("---------son---------");
	}

	@Override
	@Bean("222")
	public F getF()
	{
		return new F("sssssss");
	}
}
