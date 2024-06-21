package com.ynan;

import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;
import org.junit.validator.AnnotationValidator;

import java.util.List;

public class MyAnnotationValidator extends AnnotationValidator  {

    @Override
    public List<Exception> validateAnnotatedClass(TestClass testClass) {
        return super.validateAnnotatedClass(testClass);
    }

    @Override
    public List<Exception> validateAnnotatedField(FrameworkField field) {
        return super.validateAnnotatedField(field);
    }

    @Override
    public List<Exception> validateAnnotatedMethod(FrameworkMethod method) {
        return super.validateAnnotatedMethod(method);
    }
}
