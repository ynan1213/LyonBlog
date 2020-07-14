package springboot_resourceLoader;

public class ClassLoaderTest
{
    public static void main(String[] args)
    {
        ClassLoader loader1 = Thread.currentThread().getContextClassLoader();
        ClassLoader loader2 = ClassLoaderTest.class.getClassLoader();
        ClassLoader.getSystemClassLoader();
    }
}
