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

import static org.tframework.test.commons.utils.TframeworkAssertions.assertHasPropertyWithValue;

import org.junit.jupiter.api.Test;
import org.tframework.core.properties.PropertiesContainer;
import org.tframework.test.commons.annotations.SetProperties;

@SetProperties("my.cool.prop=test")
@TFrameworkTest
public class TFrameworkTestConstructorInjectionTest {

    private final PropertiesContainer propertiesContainer;

    public TFrameworkTestConstructorInjectionTest(PropertiesContainer propertiesContainer) {
        this.propertiesContainer = propertiesContainer;
    }

    @Test
    public void shouldLaunchApplication() {
        assertHasPropertyWithValue(propertiesContainer, "my.cool.prop", "test");
    }

}
