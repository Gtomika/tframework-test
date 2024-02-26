/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.validators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.reflection.annotations.AnnotationScanner;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class IsElementTestClassValidator implements TestClassValidator {

    static final String TEST_CLASS_NOT_ELEMENT_ERROR = "The test class '%s' should be marked with '" +
            Element.class.getName() + "'";

    private final AnnotationScanner annotationScanner;

    @Override
    public void validateTestClass(Class<?> testClass) throws IllegalArgumentException {
        if(!annotationScanner.hasAnnotation(testClass, Element.class)) {
            throw new IllegalStateException(TEST_CLASS_NOT_ELEMENT_ERROR.formatted(testClass.getName()));
        }
    }
}
