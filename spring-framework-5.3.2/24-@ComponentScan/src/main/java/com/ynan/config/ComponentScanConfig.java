package com.ynan.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

/**
 * @author yuannan
 */
@ComponentScan(
		basePackages = "com.ynan.service",
		basePackageClasses = ComponentScanConfig.class,
		useDefaultFilters = true,
		excludeFilters = @Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class}))
public class ComponentScanConfig {

}
