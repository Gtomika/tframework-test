/*
Copyright 2024 Tamas Gaspar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
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
