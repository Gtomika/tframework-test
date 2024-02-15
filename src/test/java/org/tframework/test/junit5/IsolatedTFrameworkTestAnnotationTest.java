/* Licensed under Apache-2.0 2024. */
package org.tframework.test.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.tframework.core.Application;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.test.junit5.annotations.IsolatedTFrameworkTest;
import org.tframework.test.junit5.annotations.SetApplicationName;

@SetApplicationName("isolatedApp")
@IsolatedTFrameworkTest
public class IsolatedTFrameworkTestAnnotationTest {

    @Test
    public void shouldLaunchSingleClassTFrameworkApplication(@InjectElement Application application) {
        assertEquals("isolatedApp", application.getName());
        assertEquals(IsolatedTFrameworkTestAnnotationTest.class, application.getRootClass());

        assertTrue(application.getElementsContainer().hasElementContext(IsolatedTFrameworkTestAnnotationTest.class));
        assertTrue(application.getElementsContainer().hasElementContext(SomeElement.class));
    }

    @Element
    public static class SomeElement {}

}
