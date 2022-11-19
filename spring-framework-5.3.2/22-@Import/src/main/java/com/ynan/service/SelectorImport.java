package com.ynan.service;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yuannan
 * @date 2022/11/17 21:47
 */
public class SelectorImport implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{
				SimpleImport.class.getName()
		};
	}
}
