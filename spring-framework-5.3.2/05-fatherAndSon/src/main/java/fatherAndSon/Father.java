package fatherAndSon;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Father
{
	@Bean
	public F getF()
	{
		return new F("fffffff");
	}

//	@Bean
//	public F getF111()
//	{
//		return new F("fffffff");
//	}
}
