/* Licensed under Apache-2.0 2024. */
package org.tframework.test.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.tframework.core.Application;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.test.commons.TestConfig;

@Element
public class TFrameworkExtensionProgrammaticTest {

    @RegisterExtension
    public static TFrameworkExtension tFrameworkExtension = TFrameworkExtension.fromConfig(
            TestConfig.builder()
                    .profiles(Set.of("test"))
                    .properties(Set.of("cool.prop=123"))
                    .applicationName("myCoolTestApp")
                    .rootClass(DummyRootClass.class)
                    .rootScanningEnabled(true)
                    .rootHierarchyScanningEnabled(false)
                    .internalScanningEnabled(false)
                    .build()
    );

    @Test
    public void shouldRun(@InjectElement Application application) {
        assertEquals("myCoolTestApp", application.getName());
        assertEquals(DummyRootClass.class, application.getRootClass());

        assertTrue(application.getProfilesContainer().isProfileSet("test"));
        assertEquals("123", application.getPropertiesContainer().getPropertyValue("cool.prop"));
    }

}
