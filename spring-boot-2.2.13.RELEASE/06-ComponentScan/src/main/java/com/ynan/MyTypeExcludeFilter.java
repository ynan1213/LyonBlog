package com.ynan;

import java.io.IOException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2022/4/1 18:13
 */
@Component // 有可能无效，因为这个时候可能还未被扫描到
public class MyTypeExcludeFilter extends TypeExcludeFilter {

	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
		if(metadataReader.getClassMetadata().getClassName().equals("com.ynan.config02.Demo2")){
			return true;
		}
		return false;
	}
}
