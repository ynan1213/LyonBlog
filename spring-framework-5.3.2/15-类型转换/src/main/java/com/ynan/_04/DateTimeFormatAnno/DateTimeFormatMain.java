package com.ynan._04.DateTimeFormatAnno;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;

/**
 * @Author yuannan
 * @Date 2021/10/30 11:43
 */
public class DateTimeFormatMain {

	public static void main(String[] args) throws NoSuchFieldException, ParseException {
		DateTimeFormatAnnotationFormatterFactory annotationFormatterFactory = new DateTimeFormatAnnotationFormatterFactory();

		// 找到该field
		Field field = Person.class.getDeclaredField("birthday");
		DateTimeFormat annotation = field.getAnnotation(DateTimeFormat.class);
		Class<?> type = field.getType();

		// 输出：
		Printer printer = annotationFormatterFactory.getPrinter(annotation, type);
		Person person = new Person(new Date());
		System.out.println(printer.print(person.getBirthday(), Locale.US));

		// 输入：
		System.out.println("输入：String -> Date====================");
		Parser parser = annotationFormatterFactory.getParser(annotation, type);
		Object output = parser.parse("2021-02-06 19:00:00", Locale.US);
		person = new Person((Date) output);
		System.out.println(person);

	}


}
