package com.ynan.service;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yuannan
 * @date 2022/11/17 21:56
 */
public class DeferredImport implements DeferredImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{
				SimpleImport.class.getName()
		};
	}
}
