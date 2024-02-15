/* Licensed under Apache-2.0 2024. */
package org.tframework.test.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.tframework.core.Application;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.test.junit5.annotations.SetApplicationName;
import org.tframework.test.junit5.annotations.TFrameworkTest;

@SetApplicationName("fullApp")
@TFrameworkTest
public class TframeworkTestAnnotationTest {

    @Test
    public void shouldLaunchFullApplication(@InjectElement Application application) {
        assertEquals("fullApp", application.getName());
        assertEquals(DummyRootClass.class, application.getRootClass());

        assertTrue(application.getElementsContainer().hasElementContext(DummyRootClass.class));
    }

}
