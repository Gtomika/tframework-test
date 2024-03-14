/* Licensed under Apache-2.0 2024. */
package org.tframework.test.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.tframework.test.commons.utils.TframeworkAssertions.assertHasElement;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.tframework.core.Application;
import org.tframework.core.elements.ElementsContainer;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.test.commons.annotations.BeforeFrameworkInitialization;
import org.tframework.test.commons.annotations.SetApplicationName;

@Slf4j
@BeforeFrameworkInitialization(callback = IsolatedTFrameworkAnnotationTest.CustomInit.class)
@SetApplicationName("isolatedApp")
@IsolatedTFrameworkTest
public class IsolatedTFrameworkAnnotationTest {

    @Test
    public void shouldLaunchSingleClassTFrameworkApplication(
            @InjectElement Application application,
            @InjectElement ElementsContainer elementsContainer
    ) {
        assertEquals("isolatedApp", application.getName());
        assertEquals(IsolatedTFrameworkAnnotationTest.class, application.getRootClass());

        assertHasElement(elementsContainer, IsolatedTFrameworkAnnotationTest.class);
        assertHasElement(elementsContainer, SomeElement.class);
    }

    @Element
    public static class SomeElement {}

    public static class CustomInit implements Runnable {

        public CustomInit() {}

        @Override
        public void run() {
            log.info("I run before the framework is initialized!");
        }
    }

}
