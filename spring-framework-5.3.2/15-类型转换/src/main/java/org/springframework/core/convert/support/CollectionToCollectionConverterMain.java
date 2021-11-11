package org.springframework.core.convert.support;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

/**
 * @Author yuannan
 * @Date 2021/10/29 20:05
 */
public class CollectionToCollectionConverterMain {

	public static void main(String[] args) {
		System.out.println("----------------CollectionToCollectionConverter---------------");
		ConditionalGenericConverter conditionalGenericConverter = new CollectionToCollectionConverter(new DefaultConversionService());

		List<String> sourceList = Arrays.asList("1", "2", "2", "3", "4");
		TypeDescriptor sourceTypeDesp = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class));
		TypeDescriptor targetTypeDesp = TypeDescriptor.collection(Set.class, TypeDescriptor.valueOf(Integer.class));

		System.out.println(conditionalGenericConverter.matches(sourceTypeDesp, targetTypeDesp));
		Object convert = conditionalGenericConverter.convert(sourceList, sourceTypeDesp, targetTypeDesp);
		System.out.println(convert.getClass());
		System.out.println(convert);
	}
}
