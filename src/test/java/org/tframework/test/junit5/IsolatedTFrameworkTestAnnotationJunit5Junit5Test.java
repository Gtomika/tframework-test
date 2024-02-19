/* Licensed under Apache-2.0 2024. */
package org.tframework.test.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.tframework.core.Application;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.test.commons.annotations.SetApplicationName;

@SetApplicationName("isolatedApp")
@IsolatedTFrameworkJunit5Test
public class IsolatedTFrameworkTestAnnotationJunit5Junit5Test {

    @Test
    public void shouldLaunchSingleClassTFrameworkApplication(@InjectElement Application application) {
        assertEquals("isolatedApp", application.getName());
        assertEquals(IsolatedTFrameworkTestAnnotationJunit5Junit5Test.class, application.getRootClass());

        assertTrue(application.getElementsContainer().hasElementContext(IsolatedTFrameworkTestAnnotationJunit5Junit5Test.class));
        assertTrue(application.getElementsContainer().hasElementContext(SomeElement.class));
    }

    @Element
    public static class SomeElement {}

}
