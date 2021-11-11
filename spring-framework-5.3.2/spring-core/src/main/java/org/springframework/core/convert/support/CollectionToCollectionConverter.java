/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.core.convert.support;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.core.CollectionFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.lang.Nullable;

/**
 * Converts from a Collection to another Collection.
 *
 * <p>First, creates a new Collection of the requested targetType with a size equal to the
 * size of the source Collection. Then copies each element in the source collection to the
 * target collection. Will perform an element conversion from the source collection's
 * parameterized type to the target collection's parameterized type if necessary.
 *
 * @author Keith Donald
 * @author Juergen Hoeller
 * @since 3.0
 */
final class CollectionToCollectionConverter implements ConditionalGenericConverter {

	private final ConversionService conversionService;

	// 唯一构造器，必须传入ConversionService：元素与元素之间的转换是依赖于conversionService转换服务去完成的
	public CollectionToCollectionConverter(ConversionService conversionService) {
		this.conversionService = conversionService;
	}


	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return Collections.singleton(new ConvertiblePair(Collection.class, Collection.class));
	}

	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		return ConversionUtils.canConvertElements(
				sourceType.getElementTypeDescriptor(), targetType.getElementTypeDescriptor(), this.conversionService);
	}

	@Override
	@Nullable
	public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		// 源集合为空，直接返回
		if (source == null) {
			return null;
		}
		Collection<?> sourceCollection = (Collection<?>) source;

		// Shortcut if possible...
		// source是集合类型，如果target的类型是source类型的子类型（或相同)，copyRequired就为false，代表不用复制了
		boolean copyRequired = !targetType.getType().isInstance(source);

		// 如果copyRequired为false，并且source集合是空的，则直接返回
		if (!copyRequired && sourceCollection.isEmpty()) {
			return source;
		}
		// elementDesc == null 代表目标集合没有指定泛型
		TypeDescriptor elementDesc = targetType.getElementTypeDescriptor();
		if (elementDesc == null && !copyRequired) {
			return source;
		}

		// At this point, we need a collection copy in any case, even if just for finding out about element copies...
		// 创建一个目标集合对象，如果没有指定泛型，则没有泛型
		Collection<Object> target = CollectionFactory.createCollection(targetType.getType(),
				(elementDesc != null ? elementDesc.getType() : null), sourceCollection.size());

		// 若目标类型没有指定泛型（没指定就是Object），不用遍历直接添加全部即可
		if (elementDesc == null) {
			target.addAll(sourceCollection);
		}
		else {
			// 遍历：一个一个元素的转，委托给conversionService去完成
			for (Object sourceElement : sourceCollection) {
				Object targetElement = this.conversionService.convert(sourceElement, sourceType.elementTypeDescriptor(sourceElement), elementDesc);
				target.add(targetElement);
				if (sourceElement != targetElement) {
					copyRequired = true;
				}
			}
		}

		return (copyRequired ? target : source);
	}

}
