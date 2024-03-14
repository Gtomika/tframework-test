/* Licensed under Apache-2.0 2024. */
package org.tframework.test.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.tframework.test.commons.utils.TframeworkAssertions.assertHasElement;

import org.junit.jupiter.api.Test;
import org.tframework.core.Application;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.test.commons.annotations.SetApplicationName;

@SetApplicationName("fullApp")
@TFrameworkTest
public class TframeworkTestAnnotationTest {

    @Test
    public void shouldLaunchFullApplication(@InjectElement Application application) {
        assertEquals("fullApp", application.getName());
        assertEquals(DummyRootClass.class, application.getRootClass());

        assertHasElement(application.getElementsContainer(), DummyRootClass.class);
    }

}
