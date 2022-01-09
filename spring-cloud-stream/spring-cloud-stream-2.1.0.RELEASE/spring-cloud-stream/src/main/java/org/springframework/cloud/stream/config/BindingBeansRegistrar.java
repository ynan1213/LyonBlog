/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BindingBeanDefinitionRegistryUtils;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * @author Marius Bogoevici
 * @author Dave Syer
 * @author Artem Bilan
 */
public class BindingBeansRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attrs = AnnotatedElementUtils
            .getMergedAnnotationAttributes(ClassUtils.resolveClassName(metadata.getClassName(), null), EnableBinding.class);
        // 遍历 @EnableBinding注解上配置的类，同一个类只会解析一次
        for (Class<?> type : collectClasses(attrs, metadata.getClassName())) {
            // 做了重复校验
            if (!registry.containsBeanDefinition(type.getName())) {
                /**
                 * 遍历类上的@Input和@Output方法，注册到容器中
                 *  1、beanName就是@Input或者@Output注解上的value值,如果没有配置value值，则取方法名，注意：里面有检测，配置的value值不能重复，因为不能有同名bean，会抛异常；
                 *  2、生成的是个工厂类，FactoryBeanName = @EnableBinding注解所在的类，FactoryMethodName就是当前方法；
                 *  3、有一点没有明白：qualifier属性添加了一个new AutowireCandidateQualifier(@Input/@Output, 注解的value)
                 * 这样就可以在容器中获取beanName为@Input或者@Output注解value值的bean
                 */
                BindingBeanDefinitionRegistryUtils.registerBindingTargetBeanDefinitions(type, type.getName(), registry);
                /**
                 * 将类注册到容器中
                 *  如果是接口，注入的是工厂类 BindableProxyFactory
                 *  如果不是接口，注入的是自己本身？？？
                 */
                BindingBeanDefinitionRegistryUtils.registerBindingTargetsQualifiedBeanDefinitions(
                    ClassUtils.resolveClassName(metadata.getClassName(), null), type, registry);
            }
        }
    }

    private Class<?>[] collectClasses(AnnotationAttributes attrs, String className) {
        EnableBinding enableBinding = AnnotationUtils.synthesizeAnnotation(attrs, EnableBinding.class, ClassUtils.resolveClassName(className, null));
        return enableBinding.value();
    }

}
