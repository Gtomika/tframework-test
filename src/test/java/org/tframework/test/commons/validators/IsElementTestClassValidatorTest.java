/* Licensed under Apache-2.0 2024. */
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
