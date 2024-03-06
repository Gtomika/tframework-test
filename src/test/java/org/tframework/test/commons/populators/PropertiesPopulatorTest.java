/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.SetProperties;

@SetProperties({"a", "b"})
@ExtendWith(MockitoExtension.class)
public class PropertiesPopulatorTest {

    @Mock
    private AnnotationScanner annotationScanner;

    @InjectMocks
    private PropertiesPopulator populator;

    @Test
    public void shouldPopulateConfig_keepingExistingValues_whenAnnotationPresent() {
        when(annotationScanner.scan(this.getClass(), SetProperties.class))
                .thenReturn(List.of(this.getClass().getAnnotation(SetProperties.class)));

        var configBuilder = TestConfig.builder()
                .properties(Set.of("c", "d"));
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals(Set.of("a", "b", "c", "d"), configBuilder.build().properties());
    }

    @Test
    public void shouldPopulateConfig_keepingExistingValues_whenAnnotationNotPresent() {
        when(annotationScanner.scan(this.getClass(), SetProperties.class))
                .thenReturn(List.of());

        var configBuilder = TestConfig.builder()
                .properties(Set.of("c", "d"));
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals(Set.of("c", "d"), configBuilder.build().properties());
    }

    @Test
    public void shouldPopulateConfig_whenNoExistingValues_andAnnotationPresent() {
        when(annotationScanner.scan(this.getClass(), SetProperties.class))
                .thenReturn(List.of(this.getClass().getAnnotation(SetProperties.class)));

        var configBuilder = TestConfig.builder();
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals(Set.of("a", "b"), configBuilder.build().properties());
    }

}
