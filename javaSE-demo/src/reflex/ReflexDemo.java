package reflex;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflexDemo {
	public static void main(String[] args) throws Exception {

		Student s = new Student("小明",20);
		
		Class<Teacher> t = Teacher.class;
		Constructor<Teacher> c = t.getConstructor();
		Teacher teacher = c.newInstance();
		Field f1 = t.getDeclaredField("tNum");
		Field f2 = t.getDeclaredField("student");
		f1.setAccessible(true);
		f2.setAccessible(true);
		f1.set(teacher, "001");
		f2.set(teacher, s);
		System.out.println(teacher);
		
	}
}
