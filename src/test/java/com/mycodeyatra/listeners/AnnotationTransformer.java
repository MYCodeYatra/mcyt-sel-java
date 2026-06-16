package com.mycodeyatra.listeners;
 
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
 
public class AnnotationTransformer implements IAnnotationTransformer {
 
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, 
                          Constructor testConstructor, Method testMethod) {
 
        // Globally assign the RetryAnalyzer to every test
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}