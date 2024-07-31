package com.ynan;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class MyExtendWithForTestTemplateInLoop implements TestTemplateInvocationContextProvider {
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    /**
     * 返回几个就执行几次
     */
    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of(new TestTemplateInvocationContext() {
                             @Override
                             public List<Extension> getAdditionalExtensions() {
                                 return Collections.emptyList();
                             }
                         },
            new TestTemplateInvocationContext() {
                @Override
                public List<Extension> getAdditionalExtensions() {
                    return Collections.emptyList();
                }
            }, new TestTemplateInvocationContext() {
                @Override
                public List<Extension> getAdditionalExtensions() {
                    return Collections.emptyList();
                }
            });
    }
}
