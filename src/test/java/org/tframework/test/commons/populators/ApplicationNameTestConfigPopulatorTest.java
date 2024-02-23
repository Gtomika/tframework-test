/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.SetApplicationName;

@SetApplicationName(ApplicationNameTestConfigPopulatorTest.TEST_NAME)
@ExtendWith(MockitoExtension.class)
public class ApplicationNameTestConfigPopulatorTest {

    public static final String TEST_NAME = "test-app";

    @Mock
    private AnnotationScanner annotationScanner;

    @InjectMocks
    private ApplicationNameTestConfigPopulator populator;

    @Test
    public void shouldPopulateConfig_whenAnnotationPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), SetApplicationName.class))
                .thenReturn(Optional.of(this.getClass().getAnnotation(SetApplicationName.class)));

        var configBuilder = TestConfig.builder();
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals(TEST_NAME, configBuilder.build().applicationName());
    }

    @Test
    public void shouldPopulateConfig_notOverridingExistingValue_whenAnnotationNotPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), SetApplicationName.class))
                .thenReturn(Optional.empty());

        var configBuilder = TestConfig.builder()
                .applicationName("other-name");
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals("other-name", configBuilder.build().applicationName());
    }

    @Test
    public void shouldPopulateConfig_overridingExistingValue_whenAnnotationPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), SetApplicationName.class))
                .thenReturn(Optional.of(this.getClass().getAnnotation(SetApplicationName.class)));

        var configBuilder = TestConfig.builder()
                .applicationName("other-name");
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals(TEST_NAME, configBuilder.build().applicationName());
    }

}
