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
