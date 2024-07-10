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
