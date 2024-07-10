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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.reflection.annotations.AnnotationScanner;

@Element
@ExtendWith(MockitoExtension.class)
public class IsElementTestClassValidatorTest {

    @Mock
    private AnnotationScanner annotationScanner;

    @InjectMocks
    private IsElementTestClassValidator validator;

    @Test
    public void shouldAcceptTestClass_whenItIsAnElement() {
        when(annotationScanner.hasAnnotation(this.getClass(), Element.class)).thenReturn(true);

        assertDoesNotThrow(() -> validator.validateTestClass(this.getClass()));
    }

    @Test
    public void shouldThrowException_whenTestClassIsNotAnElement() {
        when(annotationScanner.hasAnnotation(this.getClass(), Element.class)).thenReturn(false);

        var exception = assertThrows(IllegalStateException.class, () -> validator.validateTestClass(this.getClass()));

        assertEquals(
                IsElementTestClassValidator.TEST_CLASS_NOT_ELEMENT_ERROR.formatted(this.getClass().getName()),
                exception.getMessage()
        );
    }
}
