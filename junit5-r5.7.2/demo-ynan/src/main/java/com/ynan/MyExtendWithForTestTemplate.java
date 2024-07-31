package com.ynan;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class MyExtendWithForTestTemplate implements TestTemplateInvocationContextProvider {
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        System.out.println(" ------------- ");
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        System.out.println(" -------------- ");

        return Stream.of(new TestTemplateInvocationContext() {
            @Override
            public List<Extension> getAdditionalExtensions() {
                return Collections.singletonList(new MyParameterResolver());
            }
        });
    }
}
