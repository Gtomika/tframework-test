/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.RootClassSettings;

@RootClassSettings(
        rootClass = RootClassTestConfigPopulatorTest.class,
        findRootClassOnClasspath = true,
        useTestClassAsRoot = true
)
@ExtendWith(MockitoExtension.class)
public class RootClassTestConfigPopulatorTest {

    @Mock
    private AnnotationScanner annotationScanner;

    private RootClassTestConfigPopulator populator;

    @BeforeEach
    void setUp() {
        populator = new RootClassTestConfigPopulator(annotationScanner);
    }

    @Test
    public void shouldPopulateConfig_overridingExistingValues_whenAnnotationPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), RootClassSettings.class))
                .thenReturn(Optional.of(this.getClass().getAnnotation(RootClassSettings.class)));

        var configBuilder = TestConfig.builder()
                .rootClass(null)
                .findRootClassOnClasspath(false)
                .useTestClassAsRoot(false);
        populator.populateConfig(configBuilder, this.getClass());

        var config = configBuilder.build();
        assertEquals(this.getClass(), config.rootClass());
        assertTrue(config.findRootClassOnClasspath());
        assertTrue(config.useTestClassAsRoot());
    }

    @Test
    public void shouldPopulateConfig_keepingExistingValues_whenAnnotationNotPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), RootClassSettings.class))
                .thenReturn(Optional.empty());

        var configBuilder = TestConfig.builder()
                .rootClass(null)
                .findRootClassOnClasspath(false)
                .useTestClassAsRoot(false);
        populator.populateConfig(configBuilder, this.getClass());

        var config = configBuilder.build();
        assertNull(config.rootClass());
        assertFalse(config.findRootClassOnClasspath());
        assertFalse(config.useTestClassAsRoot());
    }

}
