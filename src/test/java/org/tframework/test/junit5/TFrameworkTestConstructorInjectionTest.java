/* Licensed under Apache-2.0 2024. */
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
