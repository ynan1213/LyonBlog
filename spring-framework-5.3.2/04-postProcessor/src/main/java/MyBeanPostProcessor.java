import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor
{
	// @Autowired
	// @Qualifier("userService2")
	// private UserService userService2;

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
	{
		System.out.println("MyBeanPostProcessor :" + beanName);
		return null;
	}
}
