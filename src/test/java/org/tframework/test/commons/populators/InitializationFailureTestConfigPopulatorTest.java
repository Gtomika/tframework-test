/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.ExpectInitializationFailure;

@ExpectInitializationFailure
@ExtendWith(MockitoExtension.class)
public class InitializationFailureTestConfigPopulatorTest {

    @Mock
    private AnnotationScanner annotationScanner;

    @InjectMocks
    private InitializationFailureTestConfigPopulator populator;

    @Test
    public void shouldPopulateConfig_overridingExistingValue_whenAnnotationPresent() {
        when(annotationScanner.hasAnnotation(this.getClass(), ExpectInitializationFailure.class)).thenReturn(true);

        var configBuilder = TestConfig.builder()
                .expectInitializationFailure(false);
        populator.populateConfig(configBuilder, this.getClass());

        assertTrue(configBuilder.build().expectInitializationFailure());
    }

    @Test
    public void shouldPopulateConfig_keepingExistingValue_whenAnnotationNotPresent() {
        when(annotationScanner.hasAnnotation(this.getClass(), ExpectInitializationFailure.class)).thenReturn(false);

        var configBuilder = TestConfig.builder()
                .expectInitializationFailure(true);
        populator.populateConfig(configBuilder, this.getClass());

        assertTrue(configBuilder.build().expectInitializationFailure());
    }
}
