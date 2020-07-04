import com.epichust.service.config.RootConfig;
import org.springframework.core.type.StandardClassMetadata;

public class TestMain
{
    public static void main(String[] args)
    {
        StandardClassMetadata data = new StandardClassMetadata(RootConfig.class);
        System.out.println(data.getClassName());
        System.out.println(data.getSuperClassName());
        System.out.println(data.getClassName());
        System.out.println(data.getClassName());
        System.out.println(data.getClassName());
        System.out.println(data.getClassName());
    }
}
